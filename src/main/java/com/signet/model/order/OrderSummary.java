package com.signet.model.order;

import java.util.Calendar;

import com.signet.model.ModelObject;

public class OrderSummary extends ModelObject {
    private static final long serialVersionUID = 1L;
    
    private String userID;
    private String number;
    private String type;
    private Calendar date;
    private String status;
    private String storeComment;
    private boolean received;
    private boolean delivered;
    private String repairJobNumber;
    private String approvedBy;
    private Calendar approvedByDate;
    private String contactName;
    private String contactPhoneNumber;
    private String billTo;
    private String billToLocation;

    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Calendar getDate() { return date; }
    public void setDate(Calendar date) { this.date = date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getStoreComment() { return storeComment; }
    public void setStoreComment(String storeComment) { this.storeComment = storeComment; }
    public boolean isReceived() { return received; }
    public void setReceived(boolean received) { this.received = received; }
    public boolean isDelivered() { return delivered; }
    public void setDelivered(boolean delivered) { this.delivered = delivered; }
    public String getRepairJobNumber() { return repairJobNumber; }
    public void setRepairJobNumber(String repairJobNumber) { this.repairJobNumber = repairJobNumber; }
    public String getApprovedBy() { return approvedBy; }
    public void setApprovedBy(String approvedBy) { this.approvedBy = approvedBy; }
    public Calendar getApprovedByDate() { return approvedByDate; }
    public void setApprovedByDate(Calendar approvedByDate) { this.approvedByDate = approvedByDate; }
    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }
    public String getContactPhoneNumber() { return contactPhoneNumber;  }
    public void setContactPhoneNumber(String contactPhoneNumber) { this.contactPhoneNumber = contactPhoneNumber; }
    public String getBillTo() { return billTo; }
    public void setBillTo(String billTo) { this.billTo = billTo; }
    public String getBillToLocation() { return billToLocation; }
    public void setBillToLocation(String billToLocation) { this.billToLocation = billToLocation; }
    public String     getUserID() { return userID; }
    public void       setUserID(String userID) { this.userID = userID; }
}
