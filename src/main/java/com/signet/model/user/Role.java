package com.signet.model.user;

import com.signet.model.ModelObject;

public class Role extends ModelObject {
    private String name;
    private String value;
    //private RoleStatusType status;

    public String getName() { return name; }
    public void   setName(String name) { this.name = name; }
    public String getValue() { return value; }
    public void   setValue(String value) { this.value = value; }
    // public RoleStatusType getStatus() { return status; }
    // public void setStatus(RoleStatusType status) { this.status = status; }
    
}
