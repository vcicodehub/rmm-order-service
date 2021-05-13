package com.signet.model.order;

import java.util.ArrayList;
import java.util.Calendar;

import com.signet.model.ModelObject;
import com.signet.model.Shop;
import com.signet.model.vendor.Vendor;

public class Order extends ModelObject {
    private static final long serialVersionUID = 1L;
    
    private String number;
    private String status;
    private String type;
    private Calendar date;
    private boolean received;
    private boolean delivered;
    private Vendor vendor;
    private Shop shop;
    private ArrayList<OrderLineItem> lineItems;

    public String     getNumber() { return number; }
    public void       setNumber(String number) { this.number = number; }
    public String     getStatus() { return status; }
    public void       setStatus(String status) { this.status = status; }
    public String     getType() { return type; }
    public void       setType(String type) {  this.type = type; }
    public Calendar   getDate() { return date; }
    public void       setDate(Calendar date) { this.date = date; }
    public boolean    isReceived() { return received; }
    public void       setReceived(boolean received) { this.received = received; }
    public boolean    isDelivered() { return delivered; }
    public void       setDelivered(boolean delivered) { this.delivered = delivered; }

    public Vendor getVendor() { return vendor; }
    public void   setVendor(Vendor vendor) { this.vendor = vendor; }
    public Shop   getShop() { return shop; }
    public void   setShop(Shop shop) { this.shop = shop; }

    public ArrayList<OrderLineItem> getLineItems() { return lineItems; }
    public void setLineItems(ArrayList<OrderLineItem> lineItems) { this.lineItems = lineItems; }
    public void addLineItem(OrderLineItem lineItem) {
      if (lineItem == null) {
          return;
      }
      if (this.lineItems == null) {
          this.lineItems = new ArrayList<OrderLineItem>();
      }
      this.lineItems.add(lineItem);
    }
    
    
}
