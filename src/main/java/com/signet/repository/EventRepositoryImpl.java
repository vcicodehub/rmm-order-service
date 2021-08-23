package com.signet.repository;

import static com.signet.util.DatabaseUtils.safeID;
import static com.signet.util.DatabaseUtils.safeNumber;
import static com.signet.util.DatabaseUtils.mapModelObject;
import static com.signet.util.DatabaseUtils.convertDateStringToCalendar;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.signet.exceptions.SignetDatabaseException;
import com.signet.model.events.Event;
import com.signet.model.events.EventType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository("eventRespository")
@Slf4j
public class EventRepositoryImpl implements EventRepository {

  @Autowired
  @Qualifier("jdbcTemplateCompiere")
  JdbcTemplate jdbcTemplate;

  /**
   * Search events using the given criteria.
   * @param Event
   * @return List<Event>
   * @throws SignetDatabaseException
   */
  @Override
  public List<Event> searchEvents(Event event) {
    StringBuffer sql = new StringBuffer()
      .append("select rmm_event_id, rmm_user_id, e_date, e_type, e_type_id, e_title, e_description, ")
      .append("e_add_user_id, e_add_date, e_mtc_user_id, e_mtc_date FROM rmm_events;");
    
    List<Map<String, Object>> eventDataList =  jdbcTemplate.query(
        sql.toString(), 
        new ColumnMapRowMapper());

    return mapEvent(eventDataList);
  }

  /**
   * Retrieves an Order with the given ID.
   * @param userID
   * @return Order
   */
  @Override
  public Event retrieveEventByID(String eventID) {

    StringBuffer sql = new StringBuffer()
      .append("select rmm_event_id, rmm_user_id, e_date, e_type, e_type_id, e_title, e_description, ")
      .append("e_add_user_id, e_add_date, e_mtc_user_id, e_mtc_date from rmm_events ")
      .append("where rmm_event_id = ?");

    List<Map<String, Object>> eventDataList =  jdbcTemplate.query(
        sql.toString(), 
        new Object[] { eventID }, 
        new int[] { Types.NUMERIC }, 
        new ColumnMapRowMapper());

    List<Event> eventList = mapEvent(eventDataList);
    Event event = null;
    if (eventList != null && eventList.size() == 1) {
      event = eventList.get(0);
    }

    return event;
  }

  @Override
  public void deleteEvent(String userID, String eventID) {
    StringBuffer sql = new StringBuffer()
      .append("DELETE FROM rmm_events WHERE rmm_event_id = ?");

    int numberOfRows = jdbcTemplate.update(sql.toString(), Integer.parseInt(eventID));

    log.info("Delete " + numberOfRows + " row(s) from rmm_event.");
  }   

  @Override
  public String createEvent(String userID, Event event) {
    if (event == null) {
      return null;
    }
    StringBuffer sql = new StringBuffer()
      .append(" INSERT INTO rmm_events (rmm_user_id, e_date, e_type, e_type_id, e_title, e_description, ")
      .append("     e_add_user_id, e_add_date, e_mtc_user_id, e_mtc_date) ")
      .append(" VALUES (?,?,?,?,?, ?,?,?,?,?)");

    jdbcTemplate.update(sql.toString(),
      event.getUserID(), 
      event.getDate(), 
      event.getType().toString(),
      Integer.parseInt(event.getTypeID()),
      event.getTitle(),
      event.getDescription(),
      userID, Calendar.getInstance(),
      userID, Calendar.getInstance()
    );

    return event.getID();
  }

  /**
   * Helper method used to map SQL result set to a collection of event objects.
   * @param eventDataList
   * @return List<Event>
   * @throws SignetDatabaseException
   * @throws SQLException
   */
  private List<Event> mapEvent(List<Map<String, Object>> eventDataList) {
    Event event = null;
    List<Event> eventList = new ArrayList<Event>();
    for(Map<String, Object> map: eventDataList){

      String eventID = safeID("rmm_event_id", map);

      event = new Event();
      event.setID(eventID);
      event.setUserID((String)map.get("rmm_user_id"));
      event.setDate(convertDateStringToCalendar("e_date", map));
      event.setType(EventType.valueOf((String)map.get("e_type")));
      event.setTypeID(safeNumber("e_type_id", map));
      event.setTitle((String)map.get("e_title"));
      event.setDescription((String)map.get("e_description"));

      mapModelObject(event, map, "e");

      eventList.add(event);
    }

    return eventList;
 }

}
