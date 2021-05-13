package com.signet.service;

import java.util.List;

import com.signet.model.order.Order;
import com.signet.repository.OrderRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("orderService")
public class OrderServiceImpl implements OrderService {

  OrderRepository repairJobRepository;

  OrderServiceImpl(OrderRepository repairJobRepository) {
    this.repairJobRepository = repairJobRepository;
  }

  @Override
  public Order createOrder(Order order) {
    log.info("createOrder()");
    return repairJobRepository.createOrder(order);
  }

  @Override
  public Order retrieveOrderByID(String id) {
    log.info("retrieveOrderByID(" + id + ")");
    return repairJobRepository.retrieveOrderByID(id);
  }

  @Override
  public List<Order> searchOrders(Order order) {
    return repairJobRepository.searchOrders(order);
  }
    
}
