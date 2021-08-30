package com.signet.service;

import java.util.List;

import com.signet.exceptions.SignetNotFoundException;
import com.signet.model.Product;
import com.signet.model.order.Order;
import com.signet.model.order.OrderLineItem;
import com.signet.model.order.OrderReceipt;
import com.signet.model.order.OrderReceiptLineItem;
import com.signet.repository.OrderRepository;
import com.signet.repository.ProductRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("orderService")
public class OrderServiceImpl implements OrderService {

  OrderRepository orderRepository;
  ProductRepository productRepository;

  OrderServiceImpl(OrderRepository repairJobRepository, ProductRepository productRepository) {
    this.orderRepository = repairJobRepository;
    this.productRepository = productRepository;
  }

  @Override
  public Order createOrder(String userID, Order order) throws SignetNotFoundException {
    log.info("createOrder()");

    Order newOrder = orderRepository.createOrder(userID, order);

    List<OrderLineItem> lineItems = order.getLineItems();
    if (lineItems != null) {
      for (OrderLineItem orderLineItem : lineItems) {
        Product product = orderLineItem.getProduct();
        if (product == null) {
          throw new SignetNotFoundException("The line item " + orderLineItem.getLineNumber() + " is missing the product.");
        }
        product = productRepository.createProduct(userID, product);
        orderLineItem.setProduct(product);

        orderLineItem.setOrderID(newOrder.getID());
        orderRepository.createOrderLineItem(userID, orderLineItem);
      }
    }
    return newOrder;
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
    
  public OrderLineItem createOrderLineItem(String userID, OrderLineItem orderLineItem) {
    return orderRepository.createOrderLineItem(userID, orderLineItem);
  }

  @Override
  public void deleteOrder(String userID, String orderID) {
    orderRepository.deleteOrder(userID, orderID);
  }

  @Override
  public OrderReceipt retrieveOrderReceiptByID(String orderReceiptID) {
    return orderRepository.retrieveOrderReceiptByID(orderReceiptID);
  }

  @Override
  public OrderReceipt createOrderReceipt(String userID, OrderReceipt orderReceipt) {
    return orderRepository.createOrderReceipt(userID, orderReceipt);
  }

  @Override
  public void deleteOrderReceipt(String userID, String orderReceiptID) {
    orderRepository.deleteOrderReceipt(userID, orderReceiptID);
  }

  @Override
  public List<OrderReceipt> searchOrderReceipts(OrderReceipt orderReceipt) {
    return orderRepository.searchOrderReceipts(orderReceipt);
  }

  @Override
  public OrderReceiptLineItem createOrderReceiptLineItem(String userID, OrderReceiptLineItem orderReceiptLineItem) {
    return orderRepository.createOrderReceiptLineItem(userID, orderReceiptLineItem);
  }
}
