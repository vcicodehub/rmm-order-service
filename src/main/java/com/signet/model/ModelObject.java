package com.signet.model;

import java.io.Serializable;
import java.util.Calendar;

public class ModelObject implements Serializable {
  private static final long serialVersionUID = 1L;

  private String   id;
  private String   addUserID;
  private Calendar addDate;
  private String   mtcUserID;
  private Calendar mtcDate;

  public String   getID() { return id; }
  public void     setID(String id) { this.id = id; }
  public String   getAddUserID() { return addUserID; }
  public void     setAddUserID(String addUserID) { this.addUserID = addUserID; }
  public Calendar getAddDate() { return addDate; }
  public void     setAddDate(Calendar addDate) { this.addDate = addDate; }
  public String   getMtcUserID() { return mtcUserID; }
  public void     setMtcUserID(String mtcUserID) { this.mtcUserID = mtcUserID; }
  public Calendar getMtcDate() { return mtcDate; }
  public void     setMtcDate(Calendar mtcDate) { this.mtcDate = mtcDate; }

  
}
