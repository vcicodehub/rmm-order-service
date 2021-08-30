package com.signet.service;

import java.util.List;

import com.signet.model.vendor.Vendor;
import com.signet.repository.VendorRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("vendorService")
public class VendorServiceImpl implements VendorService {

  VendorRepository vendorRepository;

  VendorServiceImpl(VendorRepository vendorRepository) {
    this.vendorRepository = vendorRepository;
  }

  @Override
  public Vendor createVendor(String userID, Vendor vendor) {
    log.info("createVendor()");
    return vendorRepository.createVendor(userID, vendor);
  }
    
  @Override
  public void deleteVendor(String vendorID) {
    log.info("deleteVendor()");
    vendorRepository.deleteVendor(vendorID);
  }
    
  @Override
  public Vendor retrieveVendorByID(String id) {
    log.info("retrieveVendorByID(" + id + ")");
    return vendorRepository.retrieveVendorByID(id);
  }

  @Override
  public Vendor retrieveVendorByNumber(String vendorNumber) {
    log.info("retrieveVendorByNumber(" + vendorNumber + ")");
    return vendorRepository.retrieveVendorByNumber(vendorNumber);
  }

  @Override
  public List<Vendor> searchVendors(Vendor Vendor) {
    log.info("searchVendors()");
    return vendorRepository.searchVendors(Vendor);
  }

}
