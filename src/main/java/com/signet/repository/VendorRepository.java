package com.signet.repository;

import java.util.List;

import com.signet.model.vendor.Vendor;

public interface VendorRepository {
    public Vendor retrieveVendorByID(String id);
    public Vendor createVendor(Vendor Vendor);
    public Vendor retrieveVendorByNumber(String vendorNumber);
    public void deleteVendor(String id);
    public List<Vendor> searchVendors(Vendor Vendor);
}
