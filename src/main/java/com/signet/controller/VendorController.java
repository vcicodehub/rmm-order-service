package com.signet.controller;

import java.util.List;

import com.signet.model.vendor.Vendor;
import com.signet.service.VendorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@CrossOrigin()
@RequestMapping("/signet/api/v1/om/vendors")
public class VendorController {

  VendorService vendorService;

  public VendorController(VendorService vendorService) {
    this.vendorService = vendorService;
  }

  @ExceptionHandler(RuntimeException.class) 
  public ResponseEntity<ServiceError> handleExceptions(RuntimeException ex) {
    log.error(ex.getMessage(), ex);
    ServiceError error = new ServiceError(HttpStatus.OK.value(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Vendor retrieveVendorByID(@PathVariable String id) {
    Vendor vendor = vendorService.retrieveVendorByID(id);
    return vendor;
  }

  @GetMapping(value = "/number/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Vendor retrieveVendorByNumber(@PathVariable String number) {
    Vendor vendor = vendorService.retrieveVendorByNumber(number);
    return vendor;
  }

  @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Vendor createVendor(@RequestBody Vendor vendor) {
    Vendor vendorList = vendorService.createVendor(vendor);
    return vendorList;
  }

  @DeleteMapping(value = "/{vendorID}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void deleteVendor(@PathVariable String vendorID) {
    vendorService.deleteVendor(vendorID);
  }

  @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Vendor> searchVendors(@RequestBody Vendor vendor) {
    List<Vendor> vendorList = vendorService.searchVendors(vendor);
    return vendorList;
  }

}
