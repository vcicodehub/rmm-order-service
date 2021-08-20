package com.signet.service;

import java.util.List;

import com.signet.model.order.Order;
import com.signet.repository.OrderRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("orderService")
public class OrderServiceImpl implements OrderService {

  OrderRepository orderRepository;

  OrderServiceImpl(OrderRepository repairJobRepository) {
    this.orderRepository = repairJobRepository;
  }

  @Override
  public Order createOrder(Order order) {
    log.info("createOrder()");
    return orderRepository.createOrder(order);
  }

  @Override
  public Order retrieveOrderByID(String id) {
    log.info("retrieveOrderByID(" + id + ")");
    return orderRepository.retrieveOrderByID(id);
  }

  @Override
  public List<Order> searchOrders(Order order) {
    return orderRepository.searchOrders(order);
  }
    
}
