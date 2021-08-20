package com.signet.model.vendor;

import java.math.BigDecimal;
import java.util.Calendar;

import com.signet.model.Address;
import com.signet.model.ModelObject;

public class Vendor extends ModelObject {
    private String name;
    private String number;
    private String type;
    private String status;
    private String emailAddress;
    private Address address;
    private BigDecimal paymentTermsDiscount;
    private Calendar paymentTermsNetDate;
    private int paymentTermsNetDays;

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
    public String  getStatus() { return status; }
    public void    setStatus(String status) { this.status = status; }
    public BigDecimal getPaymentTermsDiscount() { return paymentTermsDiscount; }
    public void setPaymentTermsDiscount(BigDecimal paymentTermsDiscount) { this.paymentTermsDiscount = paymentTermsDiscount; }
    public Calendar getPaymentTermsNetDate() { return paymentTermsNetDate; }
    public void setPaymentTermsNetDate(Calendar paymentTermsNetDate) { this.paymentTermsNetDate = paymentTermsNetDate; }
    public int getPaymentTermsNetDays() { return paymentTermsNetDays; }
    public void setPaymentTermsNetDays(int paymentTermsNetDays) { this.paymentTermsNetDays = paymentTermsNetDays; }
    

}
