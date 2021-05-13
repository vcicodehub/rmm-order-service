package com.signet.model.order;

import com.signet.model.Shop;

public class TransferOrder extends Order {
    private static final long serialVersionUID = 1L;
    
    private String tenant;
    private Shop shop;
    private String contactName;

    public String getTenant() { return tenant; }
    public void   setTenant(String tenant) { this.tenant = tenant; }
    public Shop   getShop() { return shop; }
    public void   setShop(Shop shop) { this.shop = shop; }
    public String getContactName() { return contactName; }
    public void   setContactName(String contactName) { this.contactName = contactName; }

    
}
