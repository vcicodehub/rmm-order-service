package com.signet.controller;

import java.util.List;

import com.signet.model.order.Order;
import com.signet.service.OrderService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/rmm/orders")
public class OrderController {

  OrderService orderService;

  public OrderController(OrderService orderService) {
    this.orderService = orderService;
  }

  @ExceptionHandler(RuntimeException.class) 
  public ResponseEntity<ServiceError> handleExceptions(RuntimeException ex) {
    log.error(ex.getMessage(), ex);
    ServiceError error = new ServiceError(HttpStatus.OK.value(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Order retrieveOrderByID(@PathVariable String id) {
    Order order = orderService.retrieveOrderByID(id);
    return order;
  }

  @PutMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public Order createOrder(@RequestBody Order order) {
    orderService.createOrder(order);
    return order;
  }

  @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Order> searchOrders(@RequestBody Order order) {
    List<Order> orderList = orderService.searchOrders(order);
    return orderList;
  }

}
