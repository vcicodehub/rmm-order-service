package com.signet.controller;

import java.util.List;

import com.signet.model.Product;
import com.signet.service.ProductService;

import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@RequestMapping("/api/v1/rmm/products")
public class ProductController {

  ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @ExceptionHandler(RuntimeException.class) 
  public ResponseEntity<ServiceError> handleExceptions(RuntimeException ex) {
    log.error(ex.getMessage(), ex);
    ServiceError error = new ServiceError(HttpStatus.OK.value(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Product retrieveProductByID(@PathVariable String id) {
    Product product = productService.retrieveProductByID(id);
    return product;
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Product createProduct(Authentication authentication, @RequestBody Product product) {
    productService.createProduct(authentication.getUsername(), product);
    return product;
  }

  @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Product> searchProducts(@RequestBody Product product) {
    List<Product> productList = productService.searchProducts(product);
    return productList;
  }

}
