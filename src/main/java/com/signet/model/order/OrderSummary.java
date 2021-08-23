package com.signet.model.order;

import java.util.Calendar;

import com.signet.model.ModelObject;

public class OrderSummary extends ModelObject {
    private static final long serialVersionUID = 1L;
    
    private String number;
    private String status;
    private String type;
    private Calendar date;
    private boolean received;
    private boolean delivered;

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

}
