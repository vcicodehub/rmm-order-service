package com.signet.service;

import java.util.List;

import com.signet.model.Product;
import com.signet.repository.ProductRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("productService")
public class ProductServiceImpl implements ProductService {

  ProductRepository productRepository;

  ProductServiceImpl(ProductRepository repairJobRepository) {
    this.productRepository = repairJobRepository;
  }

  @Override
  public Product retrieveProductByID(String productID) {
    return productRepository.retrieveProductByID(productID);
  }

  @Override
  public Product createProduct(String userID, Product product) {
    log.info("createProduct(" + userID + ")");
    return productRepository.createProduct(userID, product);
  }

  @Override
  public void deleteProduct(String userID, String productID) {
    productRepository.deleteProduct(userID, productID);
  }

  @Override
  public List<Product> searchProducts(Product product) {
    return productRepository.searchProducts(product);
  }


}
