package com.signet.util;

import java.sql.ResultSet;
import java.sql.SQLException;
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
    public static Calendar convertDateStringToCalendar(java.sql.Date date) {
      if (ObjectUtils.isEmpty(date)) {
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
      obj.setAddDate(convertDateStringToCalendar((java.sql.Date)map.get(prefix + "_add_date")));
      obj.setMtcUserID((String)map.get(prefix + "_mtc_user_id"));
      obj.setMtcDate(convertDateStringToCalendar((java.sql.Date)map.get(prefix + "_mtc_date")));
    }
}
