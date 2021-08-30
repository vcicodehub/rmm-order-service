package com.signet.repository;

import static com.signet.util.DatabaseUtils.safeID;
import static com.signet.util.DatabaseUtils.safeBoolean;
import static com.signet.util.DatabaseUtils.safeBigDecimal;
import static com.signet.util.DatabaseUtils.safeTimestamp;
import static com.signet.util.DatabaseUtils.mapModelObject;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.signet.exceptions.SignetDatabaseException;
import com.signet.model.Product;
import com.signet.model.SupplyCategory;

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
@Repository("productRespository")
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

  @Autowired
  @Qualifier("jdbcTemplateRMM")
  JdbcTemplate jdbcTemplate;

  /**
   * Search Products using the given criteria.
   * @param Product
   * @return List<Product>
   * @throws SignetDatabaseException
   */
  @Override
  public List<Product> searchProducts(Product product) {
    StringBuffer sql = new StringBuffer()
      .append("SELECT c.rmm_supply_categories_id, p_key, p_type, p_description, p_cost, ")
      .append("   p_status, p_name, p_quality, p_cut, p_size, p_shape, p_size_carat, p_color, p_ster_quality, ")
      .append("   p_add_user_id, p_add_date, p_mtc_user_id, p_mtc_date, p_last_copied_date ")
      .append("FROM rmm_product p, rmm_supply_categories c")
      .append("WHERE p.rmm_supply_categories_id = c.rmm_supply_categories_id");

    List<Map<String, Object>> productDataList =  jdbcTemplate.query(
        sql.toString(), 
        new ColumnMapRowMapper());

    return mapProduct(productDataList);
  }

  /**
   * Retrieves an Product with the given ID.
   * @param userID
   * @return Product
   */
  @Override
  public Product retrieveProductByID(String productID) {

    StringBuffer sql = new StringBuffer()
      .append("SELECT c.rmm_supply_categories_id, p_key, p_type, p_description, p_cost, ")
      .append("   p_status, p_name, p_quality, p_cut, p_size, p_shape, p_size_carat, p_color, p_ster_quality, ")
      .append("   p_add_user_id, p_add_date, p_mtc_user_id, p_mtc_date, p_last_copied_date ")
      .append("FROM rmm_product p, rmm_supply_categories c")
      .append("WHERE p.rmm_supply_categories_id = c.rmm_supply_categories_id ")
      .append("where rmm_product_id = ?");

    List<Map<String, Object>> productDataList =  jdbcTemplate.query(
        sql.toString(), 
        new Object[] { productID }, 
        new int[] { Types.NUMERIC }, 
        new ColumnMapRowMapper());

    List<Product> productList = mapProduct(productDataList);
    Product product = null;
    if (productList != null && productList.size() == 1) {
      product = productList.get(0);
    }

    return product;
  }

  @Override
  public void deleteProduct(String userID, String productID) {
    StringBuffer sql = new StringBuffer()
      .append("DELETE FROM rmm_products WHERE rmm_product_id = ?");

    int numberOfRows = jdbcTemplate.update(sql.toString(), Integer.parseInt(productID));

    log.info("Delete " + numberOfRows + " row(s) from rmm_product.");
  }   

  @Override
  public Product createProduct(String userID, Product product) {
    if (product == null) {
      return null;
    }
    StringBuffer sql = new StringBuffer()
    .append("INSERT INTO rmm_product (rmm_supply_categories_id, p_key, p_type, p_description, p_cost, ")
    .append("   p_status, p_name, p_quality, p_cut, p_size, p_shape, p_size_carat, p_color, p_ster_quality, ")
    .append("   p_add_user_id, p_add_date, p_mtc_user_id, p_mtc_date) ")
    .append("VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?)");
    
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update(connection -> {
      int index = 1;
      PreparedStatement ps = connection.prepareStatement(sql.toString(), new String[] {"rmm_product_id"});
        ps.setInt(index++, safeID(product.getSupplyCategoryID()));
        ps.setString(index++, product.getKey());
        ps.setString(index++, product.getType());
        ps.setString(index++, product.getDescription());
        ps.setBigDecimal(index++, product.getCost());
        ps.setString(index++, product.getStatus());
        ps.setString(index++, product.getName());
        ps.setString(index++, product.getQuality());
        ps.setString(index++, product.getCut());
        ps.setString(index++, product.getSize());
        ps.setString(index++, product.getShape());
        ps.setString(index++, product.getSizeCarat());
        ps.setString(index++, product.getColor());
        ps.setString(index++, product.getSterlingQuality());
        ps.setString(index++, userID);
        ps.setTimestamp(index++, safeTimestamp(Calendar.getInstance()));
        ps.setString(index++, userID);
        ps.setTimestamp(index++, safeTimestamp(Calendar.getInstance()));
        return ps;
      }, keyHolder);

    product.setID(keyHolder.getKey().toString());
    return product;
  }

  /**
   * Helper method used to map SQL result set to a collection of product objects.
   * @param productDataList
   * @return List<Product>
   * @throws SignetDatabaseException
   * @throws SQLException
   */
  private List<Product> mapProduct(List<Map<String, Object>> productDataList) {
    Product product = null;
    List<Product> productList = new ArrayList<Product>();
    for(Map<String, Object> map: productDataList){

      String productID = safeID("rmm_product_id", map);

      product = new Product();
      product.setID(productID);
      product.setDescription((String)map.get("p_description"));
      product.setKey((String)map.get("p_key"));
      product.setType((String)map.get("p_type"));
      product.setCost(safeBigDecimal("p_cost", map));
      product.setStatus((String)map.get("p_status"));
      product.setName((String)map.get("p_name"));
      product.setQuality((String)map.get("p_quality"));
      product.setCut((String)map.get("p_cut"));
      product.setSize((String)map.get("p_size"));
      product.setShape((String)map.get("p_size"));
      product.setSizeCarat((String)map.get("p_size_carat"));
      product.setColor((String)map.get("p_color"));
      product.setSterlingQuality((String)map.get("p_ster_quality"));
      mapModelObject(product, map, "p");

      SupplyCategory supplyCategory = new SupplyCategory();
      supplyCategory.setID(safeID("rmm_supply_categories_id", map));
      supplyCategory.setCategory((String)map.get("sc_category"));
      supplyCategory.setCategory((String)map.get("sc_description"));
      supplyCategory.setBulkItem(safeBoolean("sc_bulk_item", map));
      mapModelObject(supplyCategory, map, "sc");
      product.setSupplyCategory(supplyCategory);

      productList.add(product);
    }

    return productList;
 }

}
