package com.signet.repository;

import static com.signet.util.DatabaseUtils.mapModelObject;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.signet.exceptions.SignetDatabaseException;
import com.signet.model.user.Role;
import com.signet.model.user.RoleStatusType;
import com.signet.model.user.User;
import com.signet.model.user.UserStatusType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Repository("userRespository")
@Slf4j
public class UserRepositoryImpl implements UserRepository {

  @Autowired
  @Qualifier("jdbcTemplateCompiere")
  JdbcTemplate jdbcTemplate;

  /**
   * Search orders using the given criteria.
   * @param Order
   * @return List<Order>
   * @throws SignetDatabaseException
   */
  @Override
  public List<User> searchUsers(User user) {
    StringBuffer sql = new StringBuffer()
      .append(" select om_user_id, omu_name, omu_password, omu_status, omu_add_user_id, ")
      .append("        omu_add_date, omu_mtc_user_id, omu_mtc_date ")
      .append(" from om_user ");

    List<Map<String, Object>> userDataList =  jdbcTemplate.query(
        sql.toString(), 
        new ColumnMapRowMapper());

    return mapUser(userDataList);
  }

  /**
   * Retrieves an Order with the given ID.
   * @param userID
   * @return Order
   */
  @Override
  public User retrieveUserByID(String userID) {

    log.info("retrieveUser called with userID " + userID);

    StringBuffer sql = new StringBuffer()
      .append(" select om_user_id, omu_name, omu_password, omu_status, omu_add_user_id, ")
      .append("        omu_add_date, omu_mtc_user_id, omu_mtc_date ")
      .append(" from om_user ")
      .append(" where om_user_id = ? ");

    List<Map<String, Object>> userDataList =  jdbcTemplate.query(
        sql.toString(), 
        new Object[] { userID }, 
        new int[] { Types.VARCHAR }, 
        new ColumnMapRowMapper());

    List<User> userList = mapUser(userDataList);
    User user = null;
    if (userList != null && userList.size() == 1) {
      user = userList.get(0);
    }

    return user;
  }

  @Override
  public String createUser(User user) {
    if (user == null) {
      return null;
    }
    StringBuffer sql = new StringBuffer()
    .append(" INSERT INTO om_user (om_user_id, omu_name, omu_password, omu_status, ")
    .append("     omu_add_user_id, omu_add_date, omu_mtc_user_id, omu_mtc_date) ")
    .append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

    jdbcTemplate.update(sql.toString(),
      user.getUserID(), 
      user.getName(), 
      "asdf", 
      UserStatusType.ACTIVE.toString(), 
      "SYSTEM", Calendar.getInstance(),
      "SYSTEM", Calendar.getInstance()
    );

    return user.getUserID();
  }

  @Override
  public String createUserRole(User user, Role role) {
    if (user == null || role == null) {
      return null;
    }
    StringBuffer sql = new StringBuffer()
    .append(" INSERT INTO om_user_role (om_user_id, om_role_id, omur_value, ")
    .append("    omur_status, omur_add_user_id, omur_add_date, omur_mtc_user_id, omur_mtc_date) ")
    .append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

    jdbcTemplate.update(sql.toString(),
      user.getUserID(), 
      new BigDecimal(role.getID()), 
      role.getValue(), 
      RoleStatusType.ACTIVE.toString(), 
      "SYSTEM", Calendar.getInstance(),
      "SYSTEM", Calendar.getInstance()
    );

    return user.getUserID();
  }   

  /**
   * Helper method used to map SQL result set to a collection of order objects.
   * @param userDataList
   * @return List<Order>
   * @throws SignetDatabaseException
   * @throws SQLException
   */
  private List<User> mapUser(List<Map<String, Object>> userDataList) {
    User user = null;
    List<User> userList = new ArrayList<User>();
    for(Map<String, Object> map: userDataList){

      user = new User();
      user.setID((String)map.get("om_user_id"));
      user.setUserID((String)map.get("om_user_id"));
      user.setName((String)map.get("omu_name"));
      user.setStatus(UserStatusType.valueOf((String)map.get("omu_status")));

      mapModelObject(user, map, "omu");
      
      userList.add(user);
    }

    return userList;
 }

 /**
  * Retrieves a single Role object with the given roleName.
  * @param roleName
  * @return Role
  */
  public Role retrieveRoleByName(String roleName) {
    StringBuffer sql = new StringBuffer()
      .append(" select om_role_id, omr_name, omr_status, omr_add_user_id,  ")
      .append("        omr_add_date, omr_mtc_user_id, omr_mtc_date ")
      .append(" from om_role where omr_name = ? ");

    List<Map<String, Object>> userDataList =  
      jdbcTemplate.query(sql.toString(), new ColumnMapRowMapper(), roleName);

    List<Role> roleList = new ArrayList<Role>();
    for (Map<String,Object> map : userDataList) {
      Role role = new Role();
      BigDecimal id = (BigDecimal)map.get("om_role_id");
      role.setID(id.toString()); 
      role.setName((String)map.get("omr_name"));
      //role.setStatus(RoleStatusType.valueOf((String)map.get("omr_status")));

      mapModelObject(role, map, "omr");

      roleList.add(role);
    }

    return roleList.get(0);
  }
}
