package com.signet.model.order;

import java.util.ArrayList;

import com.signet.model.Shop;
import com.signet.model.vendor.Vendor;

public class Order extends OrderSummary {
    private static final long serialVersionUID = 1L;
    
    private Vendor vendor;
    private Shop shop;
    private ArrayList<OrderLineItem> lineItems;

    public Vendor getVendor() { return vendor; }
    public void   setVendor(Vendor vendor) { this.vendor = vendor; }
    public String getVendorID() {
      return (vendor == null ? null : vendor.getID());
    }
    public void setVendorID(String value) {}

    public Shop   getShop() { return shop; }
    public void   setShop(Shop shop) { this.shop = shop; }
    public String getShopID() {
      return (shop == null ? null : shop.getID());
    }
    public void setShopID(String value) {}
  
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
