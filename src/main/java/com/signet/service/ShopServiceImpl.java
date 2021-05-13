package com.signet.service;

import java.util.List;

import com.signet.model.Shop;
import com.signet.repository.ShopRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("shopService")
public class ShopServiceImpl implements ShopService {

  ShopRepository shopRepository;

  ShopServiceImpl(ShopRepository shopRepository) {
    this.shopRepository = shopRepository;
  }

  @Override
  public Shop createShop(Shop shop) {
    log.info("createShop()");
    return shopRepository.createShop(shop);
  }
    
  @Override
  public void deleteShop(String shopID) {
    log.info("deleteShop(" + shopID + ")");
    shopRepository.deleteShop(shopID);
  }
    
  @Override
  public Shop retrieveShopByID(String id) {
    log.info("retrieveShopByID(" + id + ")");
    return shopRepository.retrieveShopByID(id);
  }

  @Override
  public Shop retrieveShopByNumber(String shopNumber) {
    log.info("retrieveShopByNumber(" + shopNumber + ")");
    return shopRepository.retrieveShopByNumber(shopNumber);
  }

  @Override
  public List<Shop> searchShops(Shop shop) {
    log.info("searchShops()");
    return shopRepository.searchShops(shop);
  }

}
