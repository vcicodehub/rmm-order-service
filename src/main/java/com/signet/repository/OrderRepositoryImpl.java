package com.signet.repository;

import static com.signet.util.DatabaseUtils.safeID;
import static com.signet.util.DatabaseUtils.safeInt;
import static com.signet.util.DatabaseUtils.safeBoolean;
import static com.signet.util.DatabaseUtils.safeTimestamp;
import static com.signet.util.DatabaseUtils.safeBigDecimal;
import static com.signet.util.DatabaseUtils.mapModelObject;
import static com.signet.util.DatabaseUtils.convertDateStringToCalendar;

import java.sql.PreparedStatement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.signet.model.Product;
import com.signet.model.order.Order;
import com.signet.model.order.OrderLineItem;
import com.signet.model.order.OrderReceipt;
import com.signet.model.order.OrderReceiptLineItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository("orderRespository")
@Slf4j
public class OrderRepositoryImpl implements OrderRepository {

  @Autowired
  @Qualifier("jdbcTemplateRMM")
  JdbcTemplate jdbcTemplate;

  @Override
  public Order createOrder(String userID, Order order) {
    StringBuffer sql = new StringBuffer()
    .append("INSERT INTO rmm_order (rmm_user_id, rmm_shop_id, rmm_vendor_id, ")
    .append("       o_number, o_type, o_date, o_status, o_store_comment, o_is_received, o_is_delivered, ")
    .append("       o_repair_job_num, o_approved_by, o_approved_by_date, o_contact_name, o_contact_phone, ")
    .append("       o_bill_to, o_bill_to_location, ")
    .append("       o_add_user_id, o_add_date, o_mtc_user_id, o_mtc_date) ")
    .append("VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?)");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      int index = 1;
      PreparedStatement ps = connection.prepareStatement(sql.toString(), new String[] { "rmm_order_id" });
        ps.setString(index++, order.getUserID());
        ps.setInt(index++, safeID(order.getShopID()));
        ps.setInt(index++, safeID(order.getVendorID()));
        ps.setString(index++, order.getNumber());
        ps.setString(index++, order.getType());
        ps.setTimestamp(index++, safeTimestamp(order.getDate()));
        ps.setString(index++, order.getStatus());
        ps.setString(index++, order.getStoreComment());
        ps.setString(index++, safeBoolean(order.isReceived()));
        ps.setString(index++, safeBoolean(order.isDelivered()));
        ps.setString(index++, order.getRepairJobNumber());
        ps.setString(index++, order.getApprovedBy());
        ps.setTimestamp(index++, safeTimestamp(order.getApprovedByDate()));
        ps.setString(index++, order.getContactName());
        ps.setString(index++, order.getContactPhoneNumber());
        ps.setString(index++, order.getBillTo());
        ps.setString(index++, order.getBillToLocation());
        ps.setString(index++, userID);
        ps.setTimestamp(index++, safeTimestamp(Calendar.getInstance()));
        ps.setString(index++, userID);
        ps.setTimestamp(index++, safeTimestamp(Calendar.getInstance()));
        return ps;
      }, keyHolder);

    order.setID(keyHolder.getKey().toString());
    return order;
  }   

  @Override
  public void deleteOrder(String userID, String orderID) {
    StringBuffer sql = new StringBuffer()
      .append("DELETE from rmm_order_line_item where rmm_order_id = ?");

    int deletedRows = jdbcTemplate.update(sql.toString(), Integer.parseInt(orderID));
    log.info("Deleted " + deletedRows + " row(s) from rmm_order_line_item.");

    sql = new StringBuffer()
      .append("DELETE from om_order where rmm_order_id = ?");

    jdbcTemplate.update(sql.toString(), Integer.parseInt(orderID));
    log.info("Deleted " + deletedRows + " row(s) from om_order.");
  }   

  /**
   * Search orders using the given criteria.
   * @param Order
   * @return List<Order>
   */
  @Override
  public List<Order> searchOrders(Order order) {
    StringBuffer sql = new StringBuffer()
      .append("select o.rmm_order_id, rmm_user_id, rmm_shop_id, rmm_vendor_id, o_number, o_type, o_date, ")
      .append("o_status, o_store_comment, o_is_received, o_is_delivered, o_repair_job_num, ")
      .append("o_approved_by, o_approved_by_date, o_contact_name, o_contact_phone, o_bill_to, o_bill_to_location, ")
      .append("o_add_user_id, o_add_date, o_mtc_user_id, o_mtc_date, o_last_copied_date, ")
      .append("i.rmm_order_line_item_id, oli_product_key, oli_description, ")
      .append("oli_quantity, oli_uom, oli_line_num, oli_quantity_ordered, oli_quantity_delivered, oli_quantity_invoiced, ")
      .append("oli_add_user_id, oli_add_date, oli_mtc_user_id, oli_mtc_date, oli_last_copied_date, ")
      .append("p.rmm_product_id, p_key, p_type, p_description, p_cost, ")
      .append("p_status, p_name, p_quality, p_cut, p_size, p_shape, p_size_carat, p_color, p_ster_quality, ")
      .append("p_add_user_id, p_add_date, p_mtc_user_id, p_mtc_date, p_last_copied_date, ")
      .append("c.rmm_supply_categories_id, sc_category, sc_description, sc_bulk_item, ")
      .append("sc_add_user_id, sc_add_date, sc_mtc_user_id, sc_mtc_date, sc_last_copied_date ")
      .append("from rmm_order o, rmm_order_line_item i, rmm_product p, rmm_supply_categories c ")
      .append("where o.rmm_order_id = i.rmm_order_id ")
      .append("  AND i.rmm_product_id = p.rmm_product_id ")
      .append("  AND p.rmm_supply_categories_id = c.rmm_supply_categories_id ")
      .append("order by i.rmm_order_line_item_id ");

    List<Map<String, Object>> userDataList =  jdbcTemplate.query(
        sql.toString(), 
        new ColumnMapRowMapper());

    return mapOrder(userDataList);
  }

  /**
   * Retrieves an Order with the given ID.
   * @param id
   * @return Order
   */
  @Override
  public Order retrieveOrderByID(String id) {

    StringBuffer sql = new StringBuffer()
    .append("select o.rmm_order_id, rmm_user_id, rmm_shop_id, rmm_vendor_id, o_number, o_type, o_date, ")
    .append("o_status, o_store_comment, o_is_received, o_is_delivered, o_repair_job_num, ")
    .append("o_approved_by, o_approved_by_date, o_contact_name, o_contact_phone, o_bill_to, o_bill_to_location, ")
    .append("o_add_user_id, o_add_date, o_mtc_user_id, o_mtc_date, o_last_copied_date, ")
    .append("i.rmm_order_line_item_id, oli_product_key, oli_description, ")
    .append("oli_quantity, oli_uom, oli_line_num, oli_quantity_ordered, oli_quantity_delivered, oli_quantity_invoiced, ")
    .append("oli_add_user_id, oli_add_date, oli_mtc_user_id, oli_mtc_date, oli_last_copied_date, ")
    .append("p.rmm_product_id, p_key, p_type, p_description, p_cost, ")
    .append("p_status, p_name, p_quality, p_cut, p_size, p_shape, p_size_carat, p_color, p_ster_quality, ")
    .append("p_add_user_id, p_add_date, p_mtc_user_id, p_mtc_date, p_last_copied_date, ")
    .append("c.rmm_supply_categories_id, sc_category, sc_description, sc_bulk_item, ")
    .append("sc_add_user_id, sc_add_date, sc_mtc_user_id, sc_mtc_date, sc_last_copied_date ")
    .append("from rmm_order o, rmm_order_line_item i, rmm_product p, rmm_supply_categories c ")
    .append("where o.rmm_order_id = i.rmm_order_id ")
    .append("  AND i.rmm_product_id = p.rmm_product_id ")
    .append("  AND p.rmm_supply_categories_id = c.rmm_supply_categories_id ")
    .append("  AND o.rmm_order_id = ? ")
    .append("order by i.rmm_order_line_item_id ");

    List<Map<String, Object>> userDataList =  jdbcTemplate.query(
        sql.toString(), 
        new Object[] { id }, 
        new int[] { Types.NUMERIC }, 
        new ColumnMapRowMapper());

    List<Order> orderList = mapOrder(userDataList);
    Order order = null;
    if (orderList != null && orderList.size() == 1) {
      order = orderList.get(0);
    }

    return order;
  }

  /**
   * createOrderLineItem
   * @param userID
   * @param OrderLineItem
   * @return OrderLineItem
   */
  public OrderLineItem createOrderLineItem(String userID, OrderLineItem orderLineItem) {
    StringBuffer sql = new StringBuffer()
    .append("insert into rmm_order_line_item (rmm_order_id, rmm_product_id, ")
    .append("            oli_product_key, oli_description, oli_quantity, oli_uom, oli_line_num, ")
    .append("            oli_quantity_ordered, oli_quantity_delivered, oli_quantity_invoiced, ")
    .append("            oli_add_user_id, oli_add_date, oli_mtc_user_id, oli_mtc_date) ")
    .append("values (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)");

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      int index = 1;
      PreparedStatement ps = connection.prepareStatement(sql.toString(), new String[] {"rmm_order_line_item_id"});
        ps.setInt(index++, safeID(orderLineItem.getOrderID()));
        ps.setInt(index++, safeID(orderLineItem.getProductID()));
        ps.setString(index++, orderLineItem.getProductKey());
        ps.setString(index++, orderLineItem.getDescription());
        ps.setInt(index++, orderLineItem.getQuantityOrdered());
        ps.setString(index++, orderLineItem.getUnitOfMeasure());
        ps.setInt(index++, orderLineItem.getLineNumber());
        ps.setInt(index++, orderLineItem.getQuantityOrdered());
        ps.setInt(index++, orderLineItem.getQuantityDelivered());
        ps.setInt(index++, orderLineItem.getQuantityInvoiced());
        ps.setString(index++, userID);
        ps.setTimestamp(index++, safeTimestamp(Calendar.getInstance()));
        ps.setString(index++, userID);
        ps.setTimestamp(index++, safeTimestamp(Calendar.getInstance()));
        return ps;
      }, keyHolder);

    orderLineItem.setID(keyHolder.getKey().toString());
    return orderLineItem;
  }

  /**
   * Helper method used to map SQL result set to a collection of order objects.
   * @param userDataList
   * @return List<Order>
   */
  private List<Order> mapOrder(List<Map<String, Object>> orderDataList) {
    Order order = null;
    String currentOrderID = null;
    List<Order> orderList = new ArrayList<Order>();
    for(Map<String, Object> map: orderDataList){

      String orderID = safeID("rmm_order_id", map);
      if (orderID == null || !orderID.equals(currentOrderID)) {
        currentOrderID = orderID;
        order = new Order();
        order.setID(safeID("rmm_order_id", map));
        order.setUserID((String)map.get("rmm_user_id"));
        order.setShopID(safeID("rmm_shop_id", map));
        order.setVendorID(safeID("rmm_vendor_id", map));
        order.setNumber((String)map.get("o_number"));
        order.setType((String)map.get("o_type"));
        order.setDate(convertDateStringToCalendar("o_date", map));
        order.setStatus((String)map.get("p_status"));
        order.setStoreComment((String)map.get("o_store_comment"));
        order.setReceived(safeBoolean("o_is_received", map));
        order.setDelivered(safeBoolean("o_is_delivered", map));
        order.setRepairJobNumber((String)map.get("o_repair_job_num"));
        order.setApprovedBy((String)map.get("o_approved_by"));
        order.setApprovedByDate(convertDateStringToCalendar("o_approved_by_date", map));
        order.setContactName((String)map.get("o_contact_name"));
        order.setContactPhoneNumber((String)map.get("o_contact_phone"));
        order.setBillTo((String)map.get("o_bill_to"));
        order.setBillToLocation((String)map.get("o_bill_to_location"));
        mapModelObject(order, map, "o");

        orderList.add(order);
      }

      OrderLineItem orderLineItem = new OrderLineItem();
      orderLineItem.setID(safeID("rmm_order_line_item_id", map));
      orderLineItem.setProductKey((String)map.get("oli_product_key"));
      orderLineItem.setDescription((String)map.get("oli_description"));
      //orderLineItem.setQuantityOrdered((int)map.get("oli_quantity"));
      orderLineItem.setUnitOfMeasure((String)map.get("oli_uom"));
      orderLineItem.setLineNumber(safeInt("oli_line_num", map));
      orderLineItem.setQuantityOrdered(safeInt("oli_quantity_ordered", map));
      orderLineItem.setQuantityDelivered(safeInt("oli_quantity_delivered", map));
      orderLineItem.setQuantityInvoiced(safeInt("oli_quantity_invoiced", map));
      mapModelObject(orderLineItem, map, "oli");

      Product product = new Product();
      product.setID(safeID("rmm_product_id", map));
      product.setKey((String)map.get("p_key"));
      product.setDescription((String)map.get("p_description"));
      product.setCost(safeBigDecimal("p_cost", map));
      product.setStatus((String)map.get("p_status"));
      product.setName((String)map.get("p_name"));
      product.setQuality((String)map.get("p_quality"));
      product.setCut((String)map.get("p_cut"));
      product.setSize((String)map.get("p_size"));
      product.setShape((String)map.get("p_shape"));
      product.setSizeCarat((String)map.get("p_size_carat"));
      product.setColor((String)map.get("p_color"));
      product.setSterlingQuality((String)map.get("p_ster_quality"));
      mapModelObject(product, map, "p");

      orderLineItem.setProduct(product);

      order.addLineItem(orderLineItem);
    }

    return orderList;
 }

  @Override
  public OrderReceipt retrieveOrderReceiptByID(String id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public OrderReceipt createOrderReceipt(String userID, OrderReceipt orderReceipt) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void deleteOrderReceipt(String userID, String orderReceiptID) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<OrderReceipt> searchOrderReceipts(OrderReceipt orderReceipt) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public OrderReceiptLineItem createOrderReceiptLineItem(String userID, OrderReceiptLineItem orderReceiptLineItem) {
    // TODO Auto-generated method stub
    return null;
  }

}
