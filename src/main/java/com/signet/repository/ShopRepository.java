package com.signet.repository;

import java.util.List;

import com.signet.model.Shop;

public interface ShopRepository {
    public Shop retrieveShopByID(String id);
    public Shop retrieveShopByNumber(String shopNumber);
    public Shop createShop(Shop shop);
    public void deleteShop(String shopID);
    public List<Shop> searchShops(Shop shop);
}
