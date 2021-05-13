package com.signet.model;

public class Shop extends ModelObject {
    private String number;
    private String name;
    private String storeNumber;
    private Address address;

    public String  getNumber() { return number; }
    public void    setNumber(String number) { this.number = number; }
    public String  getStoreNumber() { return storeNumber; }
    public void    setStoreNumber(String storeNumber) { this.storeNumber = storeNumber; }
    public String  getName() { return name; }
    public void    setName(String name) { this.name = name; }
    public Address getAddress() { return address; }
    public void    setAddress(Address address) { this.address = address; }
}
