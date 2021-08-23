package com.signet.controller;

import java.util.List;

import com.signet.exceptions.SignetServiceException;
import com.signet.model.events.Event;
import com.signet.service.EventService;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@RequestMapping("/api/v1/rmm/events")
public class EventController {

  EventService eventService;

  public EventController(EventService eventService) {
    this.eventService = eventService;
  }

  @ExceptionHandler(RuntimeException.class) 
  public ResponseEntity<ServiceError> handleExceptions(RuntimeException ex) {
    ServiceError error = new ServiceError(HttpStatus.OK.value(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.OK);
  }

  @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Event> searchEvents(@RequestBody Event event) throws SignetServiceException {
    log.info("searchEvents()");
    return eventService.searchEvents(event);
  }

  @GetMapping(value = "/{eventID}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Event retrieveEventByID(@PathVariable String eventID) throws SignetServiceException {
    log.info("retrieveEventByID()");
    return eventService.retrieveEventByID(eventID);
  }

  @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public String createEvent(@RequestBody Event event) {
    log.info("createEvent()");
    return eventService.createEvent("SYSTEM", event);
  }

  @DeleteMapping(value = "/{eventID}", produces = MediaType.APPLICATION_JSON_VALUE)
  public void deleteEvent(@PathVariable String eventID) {
    log.info("deleteEvent(" + eventID + ")");
    eventService.deleteEvent("SYSTEM", eventID);
  }
}
