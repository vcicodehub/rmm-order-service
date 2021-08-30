package com.signet.model.order;

import java.util.Calendar;

import com.signet.model.ModelObject;

public class OrderReceiptLineItem extends ModelObject {
    private static final long serialVersionUID = 1L;
    
    private String orderReceiptID;
    private String orderLineItemID;
    private Calendar date;
    private String status;
    private int quantity;
    private String unitOfMeasure;

    public String   getOrderReceiptID() { return orderReceiptID;  }
    public void     setOrderReceiptID(String orderReceiptID) {this.orderReceiptID = orderReceiptID; }
    public String   getOrderLineItemID() { return orderLineItemID; }
    public void     setOrderLineItemID(String orderLineItemID) { this.orderLineItemID = orderLineItemID; }
    public Calendar getDate() { return date; }
    public void     setDate(Calendar date) { this.date = date; }
    public String   getStatus() { return status; }
    public void     setStatus(String status) { this.status = status; }
    public int      getQuantity() { return quantity; }
    public void     setQuantity(int quantity) { this.quantity = quantity; }
    public String   getUnitOfMeasure() { return unitOfMeasure; }
    public void     setUnitOfMeasure(String unitOfMeasure) { this.unitOfMeasure = unitOfMeasure; }

}
