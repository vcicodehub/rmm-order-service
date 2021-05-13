package com.signet.model.order;

import com.signet.model.vendor.Vendor;

public class VendorOrder extends Order {
    private static final long serialVersionUID = 1L;
    
    private Vendor vendor;

    public Vendor getVendor() {  return vendor; }
    public void   setVendor(Vendor vendor) { this.vendor = vendor; }
    
}
