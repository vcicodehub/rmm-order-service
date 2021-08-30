package com.signet.service;

import java.util.List;

import com.signet.model.vendor.Vendor;

public interface VendorService {
    public Vendor retrieveVendorByID(String id);
    public Vendor retrieveVendorByNumber(String vendorNumber);
    public Vendor createVendor(String userID, Vendor vendor);
    public void deleteVendor(String vendorID);
    public List<Vendor> searchVendors(Vendor vendor);
}
