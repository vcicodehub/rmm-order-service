package com.signet.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.signet.model.order.OrderSummary;

public class UserProfile implements Serializable {
  private User user;
  private List<OrderSummary> orderHistory = new ArrayList<OrderSummary>();

  public User getUser() { return user; }
  public void setUser(User user) { this.user = user; }
  public List<OrderSummary> getOrderHistory() { return orderHistory; }
  public void setOrderHistory(List<OrderSummary> orderHistory) { this.orderHistory = orderHistory; }

}
