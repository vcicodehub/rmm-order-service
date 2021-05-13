package com.signet.model.user;

import java.util.ArrayList;
import java.util.List;

import com.signet.model.ModelObject;

public class User extends ModelObject {
    private String userID;
    private String name;
    private List<Role> roles;
    private UserStatusType status;

    public String getUserID() { return userID; }
    public void setUserID(String userID) { this.userID = userID; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public UserStatusType getStatus() { return status; }
    public void setStatus(UserStatusType status) { this.status = status; }

    public List<Role> getRoles() { return roles; }
    public void setRoles(List<Role> roles) { this.roles = roles; }
    public void addRole(Role role) {
      if (role != null) {
        if (roles == null) {
          roles = new ArrayList<Role>();
        }
        roles.add(role);
      }
    }
    
}
