package com.signet.repository;

import java.util.List;

import com.signet.model.Product;

public interface ProductRepository {
    public Product retrieveProductByID(String id);
    public List<Product> searchProducts(Product product);
    public Product createProduct(String userID, Product product);
    public void deleteProduct(String userID, String productID);
}
