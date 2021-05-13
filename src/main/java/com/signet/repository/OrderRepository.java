package com.signet.repository;

import java.util.List;

import com.signet.model.order.Order;

public interface OrderRepository {
    public Order retrieveOrderByID(String id);
    public Order createOrder(Order order);
    public void deleteOrder(String orderID);
    public List<Order> searchOrders(Order order);
}
