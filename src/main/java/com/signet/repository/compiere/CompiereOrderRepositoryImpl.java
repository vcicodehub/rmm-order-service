package com.signet.repository.compiere;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.signet.model.Product;
import com.signet.model.Shop;
import com.signet.model.order.Order;
import com.signet.model.order.OrderLineItem;
import com.signet.model.vendor.Vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository("compiereOrderRespository")
@Slf4j
public class CompiereOrderRepositoryImpl implements CompiereOrderRepository {

  @Autowired
  @Qualifier("jdbcTemplateRMM")
  JdbcTemplate jdbcTemplate;

  /**
   * Search orders using the given criteria.
   * @param Order
   * @return List<Order>
   */
  @Override
  public List<Order> searchOrders(Order order) {
    StringBuffer sql = new StringBuffer()
      .append("select o.C_ORDER_ID, o.CREATED, o.DOCUMENTNO, C_ORDERLINE_ID, l.QTYORDERED,  ")
      .append("g.AD_ORG_ID, g.NAME as SHOP_NAME, g.SHOPNO, ")
      .append("p.M_PRODUCT_ID, p.DESCRIPTION, PRICEACTUAL, c.VALUE, b.NAME ")
      .append("from C_ORDER o, C_BPARTNER b, C_ORDERLINE l, AD_ORG g, AD_ORGINFO gi, M_PRODUCT p, M_PRODUCT_CATEGORY c ")
      .append("where o.C_ORDER_ID = l.C_ORDER_ID ")
      .append("  AND o.AD_ORG_ID = g.AD_ORG_ID ")
      .append("  AND g.AD_ORG_ID = gi.AD_ORG_ID ")
      .append("  AND o.C_BPARTNER_ID = b.C_BPARTNER_ID ")
      .append("  AND l.M_PRODUCT_ID = p.M_PRODUCT_ID ")
      .append("  AND p.M_PRODUCT_CATEGORY_ID = c.M_PRODUCT_CATEGORY_ID ")
      .append("  AND o.CREATED > TO_DATE('08-01-2020','MM-DD-YYYY') ")
      .append("order by l.C_ORDERLINE_ID, c.M_PRODUCT_CATEGORY_ID");

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

    log.info("retrieveOrder called with id " + id);

    StringBuffer sql = new StringBuffer()
      .append("select o.C_ORDER_ID, o.CREATED, o.DOCUMENTNO, C_ORDERLINE_ID, l.QTYORDERED,  ")
      .append("g.AD_ORG_ID, g.NAME as SHOP_NAME, g.SHOPNO, ")
      .append("p.M_PRODUCT_ID, p.DESCRIPTION, PRICEACTUAL, c.VALUE, b.NAME ")
      .append("from C_ORDER o, C_BPARTNER b, C_ORDERLINE l, AD_ORG g, AD_ORGINFO gi, M_PRODUCT p, M_PRODUCT_CATEGORY c ")
      .append("where o.C_ORDER_ID = l.C_ORDER_ID ")
      .append("  AND o.AD_ORG_ID = g.AD_ORG_ID ")
      .append("  AND g.AD_ORG_ID = gi.AD_ORG_ID ")
      .append("  AND o.C_BPARTNER_ID = b.C_BPARTNER_ID ")
      .append("  AND l.M_PRODUCT_ID = p.M_PRODUCT_ID ")
      .append("  AND p.M_PRODUCT_CATEGORY_ID = c.M_PRODUCT_CATEGORY_ID ")
      .append("  AND o.C_ORDER_ID = ? ")
      .append("order by l.C_ORDERLINE_ID, c.M_PRODUCT_CATEGORY_ID");

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
   * Helper method used to map SQL result set to a collection of order objects.
   * @param userDataList
   * @return List<Order>
   */
  private List<Order> mapOrder(List<Map<String, Object>> userDataList) {
    BigDecimal rowID = null;
    BigDecimal orderRowID = null;
    Order order = null;
    BigDecimal lineItemRowID = null;
    ArrayList<Order> orderList = new ArrayList<Order>();
    OrderLineItem lineItem = null;
    for(Map<String, Object> map: userDataList){
      rowID = (BigDecimal)map.get("C_ORDER_ID");
      if (!rowID.equals(orderRowID)) {
        order = new Order();
        orderRowID = rowID;
        order.setID(rowID.toString());
        order.setNumber((String)map.get("DOCUMENTNO"));
        orderList.add(order);

        Shop shop = new Shop();
        BigDecimal shopID = (BigDecimal)map.get("AD_ORG_ID");
        shop.setID(shopID.toString());
        shop.setName((String)map.get("SHOP_NAME"));
        shop.setNumber((String)map.get("SHOPNO"));
        order.setShop(shop);

        Vendor vendor =  new Vendor();
        vendor.setName((String)map.get("NAME"));
        order.setVendor(vendor);
      }

      rowID = (BigDecimal)map.get("C_ORDERLINE_ID");
      if (!rowID.equals(lineItemRowID)) {
        lineItemRowID = rowID;
        lineItem = new OrderLineItem();
        lineItem.setID(rowID.toString());
        BigDecimal count = (BigDecimal)map.get("QTYORDERED");
        lineItem.setQuantityOrdered(count.intValue());
        order.addLineItem(lineItem);
      }

      Product product = new Product();
      rowID = (BigDecimal)map.get("M_PRODUCT_ID");
      product.setID(rowID.toString());
      product.setCost((BigDecimal)map.get("PRICEACTUAL"));
      //product.setCategory((String)map.get("VALUE"));
      product.setDescription((String)map.get("DESCRIPTION"));
      
      lineItem.setProduct(product);
    }

    return orderList;
 }   

}
