package com.signet.model.events;

import java.util.Calendar;

import com.signet.model.ModelObject;

public class Event extends ModelObject {
  private String userID;
  private Calendar date;
  private EventType type;
  private String typeID;
  private String title;
  private String description;

  public String    getUserID() { return userID; }
  public void      setUserID(String userID) { this.userID = userID; }
  public Calendar  getDate() { return date; }
  public void      setDate(Calendar date) { this.date = date; }
  public EventType getType() { return type; }
  public void      setType(EventType type) { this.type = type; }
  public String    getTypeID() { return typeID; }
  public void      setTypeID(String typeID) { this.typeID = typeID; }
  public String    getTitle() { return title; }
  public void      setTitle(String title) { this.title = title; }
  public String    getDescription() { return description; }
  public void      setDescription(String description) { this.description = description; }

}
