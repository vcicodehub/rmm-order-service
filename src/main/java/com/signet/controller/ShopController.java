package com.signet.controller;

import java.util.List;

import com.signet.model.Shop;
import com.signet.service.ShopService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/rmm/shops")
public class ShopController {

  ShopService shopService;

  public ShopController(ShopService shopService) {
    this.shopService = shopService;
  }

  @ExceptionHandler(RuntimeException.class) 
  public ResponseEntity<ServiceError> handleExceptions(RuntimeException ex) {
    log.error(ex.getMessage(), ex);
    ServiceError error = new ServiceError(HttpStatus.OK.value(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Shop retrieveShopByID(@PathVariable String id) {
    Shop shop = shopService.retrieveShopByID(id);
    return shop;
  }

  @GetMapping(value = "/number/{number}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Shop retrieveShopByNumber(@PathVariable String shopNumber) {
    Shop shop = shopService.retrieveShopByNumber(shopNumber);
    return shop;
  }

  @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Shop createShop(@RequestBody Shop shop) {
    Shop shopList = shopService.createShop(shop);
    return shopList;
  }

  @DeleteMapping(value = "/{shopID}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void deleteShop(@PathVariable String shopID) {
    shopService.deleteShop(shopID);
  }

  @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Shop> searchShops(@RequestBody Shop shop) {
    List<Shop> shopList = shopService.searchShops(shop);
    return shopList;
  }

}
