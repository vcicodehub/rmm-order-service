package com.signet.repository;

import static com.signet.util.DatabaseUtils.mapModelObject;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.signet.model.Address;
import com.signet.model.Shop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository("shopRespository")
@Slf4j
public class ShopRepositoryImpl implements ShopRepository {

  @Autowired
  @Qualifier("jdbcTemplateCompiere")
  JdbcTemplate jdbcTemplate;

  @Override
  public void deleteShop(String shopID) {
    StringBuffer sql = new StringBuffer()
      .append("DELETE FROM om_shop WHERE om_shop_id = ?");

    int numberRowsDeleted = jdbcTemplate.update(sql.toString(), new BigDecimal(shopID));

    log.info("Deleted " + numberRowsDeleted + " row(s) from om_shop.");
  }

  @Override
  public Shop createShop(Shop shop) {
    StringBuffer sql = new StringBuffer()
      .append(" INSERT INTO om_shop (om_shop_id, oms_number, oms_store_number, oms_name, oms_addr_line1,  ")
      .append("        oms_addr_line2, oms_addr_city, oms_addr_state, oms_addr_zip, oms_status,  ")
      .append("        oms_add_user_id, oms_add_date, oms_mtc_user_id, oms_mtc_date)  ")
      .append(" VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)  ");

    Address address = shop.getAddress();
    jdbcTemplate.update(sql.toString(), 
        new BigDecimal(shop.getID()),
        shop.getNumber(),
        shop.getStoreNumber(),
        shop.getName(),
        address.getLine1(),
        address.getLine2(),
        address.getCity(),
        address.getState(),
        address.getZip(),
        "ACTIVE",
        shop.getAddUserID(),
        shop.getAddDate(),
        shop.getMtcUserID(),
        shop.getMtcDate());

    log.info("Created new shop " + shop.getID());

    return shop;
  }   

  /**
   * Search shops using the given criteria.
   * @param Shop
   * @return List<Shop>
   */
  @Override
  public List<Shop> searchShops(Shop shop) {
    StringBuffer sql = new StringBuffer()
      .append(" SELECT om_shop_id, oms_number, oms_store_number, oms_name, oms_addr_line1,  ")
      .append("        oms_addr_line2, oms_addr_city, oms_addr_state, oms_addr_zip, oms_status,  ")
      .append("        oms_add_user_id, oms_add_date, oms_mtc_user_id, oms_mtc_date  ")
      .append(" FROM om_shop ");

    List<Map<String, Object>> userDataList =  jdbcTemplate.query(
        sql.toString(), 
        new ColumnMapRowMapper());

    return mapShop(userDataList);
  }

  /**
   * Retrieves a Shop with the given ID.
   * @param id
   * @return Shop
   */
  @Override
  public Shop retrieveShopByID(String id) {

    StringBuffer sql = new StringBuffer()
    .append(" SELECT om_shop_id, oms_number, oms_store_number, oms_name, oms_addr_line1,  ")
    .append("        oms_addr_line2, oms_addr_city, oms_addr_state, oms_addr_zip, oms_status,  ")
    .append("        oms_add_user_id, oms_add_date, oms_mtc_user_id, oms_mtc_date  ")
    .append(" FROM om_shop ")
    .append(" WHERE om_shop_id = ?");

    List<Map<String, Object>> userDataList =  jdbcTemplate.query(
        sql.toString(), 
        new Object[] { new BigDecimal(id) }, 
        new int[] { Types.NUMERIC }, 
        new ColumnMapRowMapper());

    List<Shop> shopList = mapShop(userDataList);
    Shop shop = null;
    if (shopList != null && shopList.size() == 1) {
      shop = shopList.get(0);
    }

    return shop;
  }

  /**
   * Retrieves a Shop with the given shop number.
   * @param shopNumber
   * @return Shop
   */
  @Override
  public Shop retrieveShopByNumber(String shopNumber) {

    StringBuffer sql = new StringBuffer()
    .append(" SELECT om_shop_id, oms_number, oms_store_number, oms_name, oms_addr_line1,  ")
    .append("        oms_addr_line2, oms_addr_city, oms_state, oms_zip, oms_status,  ")
    .append("        oms_add_user_id, oms_add_date, oms_mtc_user_id, oms_mtc_date  ")
    .append(" FROM om_shop ")
    .append(" WHERE oms_number = ?");

    List<Map<String, Object>> userDataList =  jdbcTemplate.query(
        sql.toString(), 
        new Object[] { shopNumber }, 
        new int[] { Types.VARCHAR }, 
        new ColumnMapRowMapper());

    List<Shop> shopList = mapShop(userDataList);
    Shop shop = null;
    if (shopList != null && shopList.size() == 1) {
      shop = shopList.get(0);
    }

    return shop;
  }

  /**
   * Helper method used to map SQL result set to a collection of shop objects.
   * @param userDataList
   * @return List<Shop>
   */
  private List<Shop> mapShop(List<Map<String, Object>> userDataList) {
    ArrayList<Shop> shopList = new ArrayList<Shop>();

    for(Map<String, Object> map: userDataList){
        Shop shop = new Shop();
        BigDecimal shopID = (BigDecimal)map.get("om_shop_id");
        shop.setID(shopID.toString());
        shop.setName((String)map.get("oms_name"));
        shop.setNumber((String)map.get("oms_number"));
        shop.setStoreNumber((String)map.get("oms_store_number"));

        Address address = new Address();
        address.setLine1((String)map.get("oms_addr_line1"));
        address.setLine2((String)map.get("oms_addr_line2"));
        address.setCity((String)map.get("oms_addr_city"));
        address.setState((String)map.get("oms_state"));
        address.setZip((String)map.get("oms_zip"));
        shop.setAddress(address);

        // Standard columns
        mapModelObject(shop, map, "oms");

        shopList.add(shop);
    }

    return shopList;
 }

}
