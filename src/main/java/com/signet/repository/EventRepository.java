package com.signet.repository;

import java.util.List;

import com.signet.model.events.Event;

public interface EventRepository {
    public Event retrieveEventByID(String id);
    public List<Event> searchEvents(Event event);
    public String createEvent(String userID, Event event);
    public void deleteEvent(String userID, String eventID);
}
