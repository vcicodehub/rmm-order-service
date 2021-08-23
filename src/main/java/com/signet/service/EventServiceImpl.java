package com.signet.service;

import java.util.List;

import com.signet.model.events.Event;
import com.signet.repository.EventRepository;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("eventService")
public class EventServiceImpl implements EventService {

  EventRepository eventRepository;

  EventServiceImpl(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  public String createEvent(String userID, Event event) {
    log.info("createEvent()");
    return eventRepository.createEvent(userID, event);
  }
    
  @Override
  public void deleteEvent(String userID, String eventID) {
    log.info("deleteEvent(" + userID + ", " + eventID + ")");
    eventRepository.deleteEvent(userID, eventID);
  }
  
  @Override
  public Event retrieveEventByID(String id) {
    log.info("retrieveEventByID(" + id + ")");
    return eventRepository.retrieveEventByID(id);
  }

  @Override
  public List<Event> searchEvents(Event event) {
    log.info("searchEvents()");
    return eventRepository.searchEvents(event);
  }

}
