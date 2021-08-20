package com.signet.repository;

import static com.signet.util.DatabaseUtils.mapModelObject;
import static com.signet.util.DatabaseUtils.safeID;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.signet.model.Address;
import com.signet.model.vendor.Vendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository("vendorRespository")
@Slf4j
public class VendorRepositoryImpl implements VendorRepository {

  @Autowired
  @Qualifier("jdbcTemplateCompiere")
  JdbcTemplate jdbcTemplate;

  @Override
  public void deleteVendor(String id) {
    StringBuffer sql = new StringBuffer()
      .append(" DELETE FROM rmm_vendor WHERE rmm_vendor_id = ?");

    int numberOfRows = jdbcTemplate.update(sql.toString(), new BigDecimal(id));

    log.info("Delete " + numberOfRows + " row(s) from om_vendor.");
  }

  @Override
  public Vendor createVendor(Vendor vendor) {
    StringBuffer sql = new StringBuffer()
      .append(" INSERT INTO rmm_vendor ( ")
      .append("rmm_vendor_id, v_type, v_status, v_number, v_name, v_email_addr, ")
      .append("v_addr_line1, v_addr_line2, v_addr_city, v_addr_state, v_addr_zip, ")
      .append("v_payterm_discount, v_payterm_net_date, v_payterm_net_days, ")
      .append("v_add_user_id, v_add_date, v_mtc_user_id, v_mtc_date, v_last_copied_date")
      .append(" VALUES (?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?) ");

    Address address = vendor.getAddress();
    jdbcTemplate.update(sql.toString(), 
        new BigDecimal(vendor.getID()),
        vendor.getType(),
        vendor.getStatus(),
        vendor.getNumber(),
        vendor.getName(),
        vendor.getEmailAddress(),
        address.getLine1(),
        address.getLine2(),
        address.getCity(),
        address.getState(),
        address.getZip(),
        vendor.getPaymentTermsDiscount(),
        vendor.getPaymentTermsNetDate(),
        vendor.getPaymentTermsNetDays(),
        vendor.getAddUserID(),
        vendor.getAddDate(),
        vendor.getMtcUserID(),
        vendor.getMtcDate(),
        vendor.getLastCopiedDate());

    log.info("Created new vendor " + vendor.getID());

    return vendor;
  }   

  /**
   * Search vendors using the given criteria.
   * @param Vendor
   * @return List<Vendor>
   */
  @Override
  public List<Vendor> searchVendors(Vendor vendor) {
    StringBuffer sql = new StringBuffer()
      .append(" SELECT ")
      .append("rmm_vendor_id, v_type, v_status, v_number, v_name, v_email_addr, ")
      .append("v_addr_line1, v_addr_line2, v_addr_city, v_addr_state, v_addr_zip, ")
      .append("v_payterm_discount, v_payterm_net_date, v_payterm_net_days, ")
      .append("v_add_user_id, v_add_date, v_mtc_user_id, v_mtc_date, v_last_copied_date ")      
      .append(" FROM rmm_vendor ");
    List<Map<String, Object>> userDataList =  jdbcTemplate.query(
        sql.toString(), 
        new ColumnMapRowMapper());

    return mapVendor(userDataList);
  }

  /**
   * Retrieves a Vendor with the given ID.
   * @param id
   * @return Vendor
   */
  @Override
  public Vendor retrieveVendorByID(String id) {

    StringBuffer sql = new StringBuffer()
    .append(" SELECT ")
    .append("rmm_vendor_id, v_type, v_status, v_number, v_name, v_email_addr, ")
    .append("v_addr_line1, v_addr_line2, v_addr_city, v_addr_state, v_addr_zip, ")
    .append("v_payterm_discount, v_payterm_net_date, v_payterm_net_days, ")
    .append("v_add_user_id, v_add_date, v_mtc_user_id, v_mtc_date, v_last_copied_date ")    
    .append(" FROM rmm_vendor ")
    .append(" WHERE rmm_vendor_id = ?");

    List<Map<String, Object>> userDataList =  jdbcTemplate.query(
        sql.toString(), 
        new Object[] { new BigDecimal(id) }, 
        new int[] { Types.NUMERIC }, 
        new ColumnMapRowMapper());

    List<Vendor> vendorList = mapVendor(userDataList);
    Vendor vendor = null;
    if (vendorList != null && vendorList.size() == 1) {
      vendor = vendorList.get(0);
    }

    return vendor;
  }

  /**
   * Retrieves a Vendor with the given vendor number.
   * @param vendorNumber
   * @return Vendor
   */
  @Override
  public Vendor retrieveVendorByNumber(String vendorNumber) {

    StringBuffer sql = new StringBuffer()
    .append(" SELECT ")
    .append("rmm_vendor_id, v_type, v_status, v_number, v_name, v_email_addr, ")
    .append("v_addr_line1, v_addr_line2, v_addr_city, v_addr_state, v_addr_zip, ")
    .append("v_payterm_discount, v_payterm_net_date, v_payterm_net_days, ")
    .append("v_add_user_id, v_add_date, v_mtc_user_id, v_mtc_date, v_last_copied_date")    
    .append(" FROM rmm_vendor ")
    .append(" WHERE v_number = ?");

    List<Map<String, Object>> userDataList =  jdbcTemplate.query(
        sql.toString(), 
        new Object[] { vendorNumber }, 
        new int[] { Types.VARCHAR }, 
        new ColumnMapRowMapper());

    List<Vendor> vendorList = mapVendor(userDataList);
    Vendor vendor = null;
    if (vendorList != null && vendorList.size() == 1) {
      vendor = vendorList.get(0);
    }

    return vendor;
  }

  /**
   * Helper method used to map SQL result set to a collection of vendor objects.
   * @param userDataList
   * @return List<Order>
   */
  private List<Vendor> mapVendor(List<Map<String, Object>> userDataList) {
    ArrayList<Vendor> vendorList = new ArrayList<Vendor>();

    for(Map<String, Object> map: userDataList){
        Vendor vendor = new Vendor();
        vendor.setID(safeID("rmm_vendor_id", map));
        vendor.setName((String)map.get("v_name"));
        vendor.setNumber((String)map.get("v_number"));

        Address address = new Address();
        address.setLine1((String)map.get("v_addr_line1"));
        address.setLine2((String)map.get("v_addr_line2"));
        address.setCity((String)map.get("v_addr_city"));
        address.setState((String)map.get("v_addr_state"));
        address.setZip((String)map.get("v_addr_zip"));
        vendor.setAddress(address);

        // Standard columns
        mapModelObject(vendor, map, "v");

        vendorList.add(vendor);
    }

    return vendorList;
 }

}
