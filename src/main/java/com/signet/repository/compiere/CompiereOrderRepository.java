package com.signet.repository.compiere;

import java.util.List;

import com.signet.model.order.Order;

public interface CompiereOrderRepository {
    public Order retrieveOrderByID(String id);
    public List<Order> searchOrders(Order order);
}
