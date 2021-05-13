package com.signet.service;

import java.util.List;

import com.signet.model.order.Order;

public interface OrderService {
    public Order retrieveOrderByID(String id);
    public Order createOrder(Order order);
    public List<Order> searchOrders(Order order);
}
