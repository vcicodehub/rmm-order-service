package com.signet.model.vendor;

import com.signet.model.Address;
import com.signet.model.ModelObject;

public class Vendor extends ModelObject {
    private String name;
    private String number;
    private String type;
    private String emailAddress;
    private Address address;

    public String  getName() { return name; }
    public void    setName(String name) { this.name = name; }
    public String  getNumber() { return number; }
    public void    setNumber(String number) { this.number = number; }
    public String  getType() { return type; }
    public void    setType(String type) { this.type = type; }
    public String  getEmailAddress() { return emailAddress; }
    public void    setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }
    public Address getAddress() { return address; }
    public void    setAddress(Address address) { this.address = address; }

}
