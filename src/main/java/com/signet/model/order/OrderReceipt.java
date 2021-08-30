package com.signet.model.order;

import java.util.ArrayList;
import java.util.Calendar;

import com.signet.model.ModelObject;

public class OrderReceipt extends ModelObject {
    private static final long serialVersionUID = 1L;
    
    private String orderID;
    private Calendar date;
    private String status;
    private ArrayList<OrderReceiptLineItem> lineItems;

    public String   getOrderID() { return orderID; }
    public void     setOrderID(String orderID) { this.orderID = orderID; }
    public Calendar getDate() { return date; }
    public void     setDate(Calendar date) { this.date = date; }
    public String   getStatus() { return status; }
    public void     setStatus(String status) { this.status = status; }
    
    public ArrayList<OrderReceiptLineItem> getLineItems() { return lineItems; }
    public void setLineItems(ArrayList<OrderReceiptLineItem> lineItems) { this.lineItems = lineItems; }
    
}
