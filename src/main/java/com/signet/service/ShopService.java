package com.signet.service;

import java.util.List;

import com.signet.model.Shop;

public interface ShopService {
    public Shop retrieveShopByID(String id);
    public Shop retrieveShopByNumber(String shopNumber);
    public Shop createShop(Shop shop);
    public void deleteShop(String shopID);
    public List<Shop> searchShops(Shop shop);
}
