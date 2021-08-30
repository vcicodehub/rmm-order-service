package com.signet.service;

import java.util.List;

import com.signet.exceptions.SignetServiceException;
import com.signet.model.order.Order;
import com.signet.model.order.OrderLineItem;
import com.signet.model.order.OrderReceipt;
import com.signet.model.order.OrderReceiptLineItem;

public interface OrderService {
    public Order retrieveOrderByID(String id);
    public Order createOrder(String userID, Order order) throws SignetServiceException;
    public void deleteOrder(String userID, String orderID);
    public List<Order> searchOrders(Order order);

    public OrderLineItem createOrderLineItem(String userID, OrderLineItem orderLineItem);

    public OrderReceipt retrieveOrderReceiptByID(String id);
    public OrderReceipt createOrderReceipt(String userID, OrderReceipt orderReceipt);
    public void deleteOrderReceipt(String userID, String orderReceiptID);
    public List<OrderReceipt> searchOrderReceipts(OrderReceipt orderReceipt);

    public OrderReceiptLineItem createOrderReceiptLineItem(String userID, OrderReceiptLineItem orderReceiptLineItem);
}
