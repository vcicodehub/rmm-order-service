package com.signet.service;

import java.util.List;

import com.signet.model.Product;

public interface ProductService {
    public Product retrieveProductByID(String id);
    public Product createProduct(String userID, Product product);
    public void deleteProduct(String userID, String productID);
    public List<Product> searchProducts(Product product);
}
