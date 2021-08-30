package com.signet.util;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

import com.signet.exceptions.SignetDatabaseException;
import com.signet.model.ModelObject;

import org.springframework.util.ObjectUtils;

public class DatabaseUtils {

  /**
   * Safely converts the given primary key (Postgres SERIAL) to a String.
   * @param columnName
   * @param map
   * @return String
   */
  public static String safeID(String columnName, Map<String, Object> map) {
    if (columnName == null || map == null || map.isEmpty()) {
      return null;
    }

    Integer id = (Integer)map.get(columnName);
    return id == null ? null : Integer.toString(id);
  }

  /**
   * Safely converts the given ID to an Integer safe for storing in PostgreSQL.
   * @param columnName
   * @param map
   * @return String
   */
  public static Integer safeID(String id) {
    if (id == null || id.isEmpty()) {
      return 0;
    }
    return Integer.parseInt(id);
  }

  /**
   * Safely converts the given Calendar to a Postgres Timestamp.
   * @param columnName
   * @param map
   * @return String
   */
  public static Timestamp safeTimestamp(Calendar date) {
    if (date == null) {
      return null;
    }

    return new Timestamp(date.getTimeInMillis());
  }

  /**
   * Safely converts the given char(1) to a boolean.
   * @param columnName
   * @param map
   * @return String
   */
  public static boolean safeBoolean(String columnName, Map<String, Object> map) {
    if (columnName == null || map == null || map.isEmpty()) {
      return false;
    }

    String id = (String)map.get(columnName);
    return id == null ? false : (id.equalsIgnoreCase("y") ? true : false);
  }

  /**
   * Safely converts the given char(1) to a boolean.
   * @param columnName
   * @param map
   * @return String
   */
  public static String safeBoolean(boolean boulean) {
    return boulean ? "Y" : "N";
  }

  /**
   * Safely converts the given column that is defined as a Postgres 
   * NUMBER to a Sting.
   * @param columnName
   * @param map
   * @return
   */
  public static String safeNumber(String columnName, Map<String, Object> map) {
    if (columnName == null || map == null || map.isEmpty()) {
      return null;
    }
    BigDecimal id = (BigDecimal)map.get(columnName);
    return id == null ? null : id.toString();
  }

  /**
   * Safely converts the given String to an int.
   * @param columnName
   * @param map
   * @return
   */
  public static int safeInt(String value) {
    if (value == null || value.isEmpty()) {
      return 0;
    }
    return Integer.parseInt(value);
  }

  /**
   * Safely converts the given column to an int.
   * @param columnName
   * @param map
   * @return int
   */
  public static int safeInt(String columnName, Map<String, Object> map) {
    if (columnName == null || map == null || map.isEmpty()) {
      return 0;
    }
    BigDecimal value = (BigDecimal)map.get(columnName);
    return (value != null) ? value.intValue() : 0;
  }

  /**
   * Safely converts the given column to a BigDecimal.
   * @param columnName
   * @param map
   * @return
   */
  public static BigDecimal safeBigDecimal(String columnName, Map<String, Object> map) {
    if (columnName == null || map == null || map.isEmpty()) {
      return null;
    }

    BigDecimal value = (BigDecimal)map.get(columnName);
    return value;
  }

  /**
   * Safely converts the given column to a BigDecimal.
   * @param value
   * @return BigDecimal
   */
  public static BigDecimal safeBigDecimal(String value) {
    if (value == null || value.isEmpty()) {
      return null;
    }
    return new BigDecimal(value);
  }

  /**
   * Convert the given Calendar to proper SQL date.
   * @param calendar
   * @return java.sql.Date
   */
  public static java.sql.Date safeDate(Calendar calendar) {
    if (calendar == null) {
        return null;
    }
    return new java.sql.Date(calendar.getTimeInMillis());
  }

  /**
   * Convert the given String into a Calendar.
   * @param dateString
   * @return Calendar
   */
  public static Calendar convertTimestampStringToCalendar(String dateString) {
      if (ObjectUtils.isEmpty(dateString)) {
          return null;
      }
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
      try {
          cal.setTime(sdf.parse(dateString));
      } 
      catch (ParseException e) {
          return null;
      }
      
      return cal;
  }

  /**
   * Convert the given String into a Calendar.
   * @param dateString
   * @return Calendar
   */
  public static Calendar convertDateStringToCalendar(String columnName, Map<String, Object> map) {
    if (ObjectUtils.isEmpty(columnName) || map == null) {
        return null;
    }
    java.sql.Date date = (java.sql.Date)map.get(columnName);
    if (date == null) {
      return null;
    }
    Calendar cal = Calendar.getInstance();
    cal.setTimeInMillis(date.getTime());
    
    return cal;
}

/**
   * Convert the given String into a Calendar.
   * @param dateString
   * @return Calendar
   */
  public static Calendar convertDateStringToCalendar(String dateString) {
      if (ObjectUtils.isEmpty(dateString)) {
          return null;
      }
      Calendar cal = Calendar.getInstance();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
      try {
          cal.setTime(sdf.parse(dateString));
      } 
      catch (ParseException e) {
          return null;
      }
      
      return cal;
  }

  /**
   * Map the standard columns to the given ModelObject instance.
   * @param obj
   * @param rs
   * @throws SQLException
   */
  public static void mapModelObject(ModelObject obj, ResultSet rs, String prefix) throws SignetDatabaseException {
    if (rs == null || obj == null || prefix == null) {
      return;
    }
    
    try {
      obj.setAddUserID(rs.getString(prefix + "_add_user_id"));
      obj.setAddDate(convertDateStringToCalendar(rs.getString(prefix + "_add_date")));
      obj.setMtcUserID(rs.getString(prefix + "_mtc_user_id"));
      obj.setMtcDate(convertDateStringToCalendar(rs.getString(prefix + "_mtc_date")));
    }
    catch (SQLException e) {
      throw new SignetDatabaseException(e.getMessage(), e);
    }
  }

  /**
   * Map the standard columns to the given ModelObject instance.
   * @param obj
   * @param rs
   * @throws SQLException
   */
  public static void mapModelObject(ModelObject obj, Map<String, Object> map, String prefix) {
    if (map == null || obj == null || prefix == null) {
      return;
    }

    obj.setAddUserID((String)map.get(prefix + "_add_user_id"));
    obj.setAddDate(convertDateStringToCalendar(prefix + "_add_date", map));
    obj.setMtcUserID((String)map.get(prefix + "_mtc_user_id"));
    obj.setMtcDate(convertDateStringToCalendar(prefix + "_mtc_date", map));
    obj.setLastCopiedDate(convertDateStringToCalendar(prefix + "_last_copied_date", map));
  }
}
