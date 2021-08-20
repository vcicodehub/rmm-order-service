package com.signet.repository;

import static com.signet.util.DatabaseUtils.mapModelObject;
import static com.signet.util.DatabaseUtils.safeID;

import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.signet.exceptions.SignetDatabaseException;
import com.signet.exceptions.SignetNotFoundException;
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
      .append("select u.rmm_user_id, u.u_name, u.u_password, u.u_status, u.u_add_user_id, ")
      .append("       u.u_add_date, u.u_mtc_user_id, u.u_mtc_date,")
      .append("       r.rmm_role_id, r.ro_name, r.ro_status, r.ro_add_user_id, r.ro_add_date, ")
      .append("       r.ro_mtc_user_id, r.ro_mtc_date, r.ro_last_copied_date ")
      .append("from rmm_user u, rmm_user_role ur, rmm_role r ")
      .append("where u.rmm_user_id = ur.rmm_user_id ")
      .append("  AND ur.rmm_role_id = r.rmm_role_id");

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
      .append("select u.rmm_user_id, u.u_name, u.u_password, u.u_status, u.u_add_user_id, ")
      .append("       u.u_add_date, u.u_mtc_user_id, u.u_mtc_date, ")
      .append("       r.rmm_role_id, r.ro_name, r.ro_status, r.ro_add_user_id, r.ro_add_date, ")
      .append("       r.ro_mtc_user_id, r.ro_mtc_date, r.ro_last_copied_date ")
      .append("from rmm_user u, rmm_user_role ur, rmm_role r ")
      .append("where u.rmm_user_id = ur.rmm_user_id ")
      .append("  AND ur.rmm_role_id = r.rmm_role_id ")
      .append("  AND u.rmm_user_id = ?");

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
  public void deleteUser(String userID) {
    StringBuffer sql = new StringBuffer()
      .append(" DELETE FROM rmm_user WHERE rmm_user_id = ?");

    int numberOfRows = jdbcTemplate.update(sql.toString(), userID);

    log.info("Delete " + numberOfRows + " row(s) from rmm_user.");
  }   

  @Override
  public String createUser(User user) {
    if (user == null) {
      return null;
    }
    StringBuffer sql = new StringBuffer()
    .append(" INSERT INTO rmm_user (rmm_user_id, u_name, u_password, u_status, ")
    .append("     u_add_user_id, u_add_date, u_mtc_user_id, u_mtc_date) ")
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
    .append(" INSERT INTO rmm_user_role (rmm_user_id, rmm_role_id, ur_value, ")
    .append("    ur_status, ur_add_user_id, ur_add_date, ur_mtc_user_id, ur_mtc_date) ")
    .append(" VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

    jdbcTemplate.update(sql.toString(),
      user.getUserID(), 
      Integer.valueOf(role.getID()), 
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

      String userID = (String)map.get("rmm_user_id");
      user = findUser(userID, userList);
      if (user == null) {
        user = new User();
        user.setID(userID);
        user.setUserID(userID);
        user.setName((String)map.get("u_name"));
        user.setStatus(UserStatusType.valueOf((String)map.get("u_status")));
        mapModelObject(user, map, "u");
        userList.add(user);
      }

      Role role = new Role();
      role.setID(safeID("rmm_role_id", map));
      role.setName((String)map.get("ro_name"));
      role.setStatus(RoleStatusType.valueOf((String)map.get("ro_status")));
      mapModelObject(role, map, "ro");
      user.addRole(role);
    }

    return userList;
 }

 private User findUser(String userID, List<User> usersList) {
   if (userID == null || usersList == null || usersList.size() == 0) {
     return null;
   }
   for (User usr : usersList) {
    String usrID = usr.getID();
    if (usrID != null && usrID.equals(userID)) {
      return usr;
    }
  }
  return null;
}

 /**
  * Retrieves a single Role object with the given roleName.
  * @param roleName
  * @return Role
 * @throws SignetNotFoundException
  */
  public Role retrieveRoleByName(String roleName) throws SignetNotFoundException {
    StringBuffer sql = new StringBuffer()
      .append(" select rmm_role_id, ro_name, ro_status, ro_add_user_id,  ")
      .append("        ro_add_date, ro_mtc_user_id, ro_mtc_date ")
      .append(" from rmm_role where ro_name = ? ");

    List<Map<String, Object>> userDataList =  
      jdbcTemplate.query(sql.toString(), new ColumnMapRowMapper(), roleName);

    if (userDataList.size() == 0) {
      throw new SignetNotFoundException("The role " + roleName + " is not a valid role.");
    }
    List<Role> roleList = new ArrayList<Role>();
    for (Map<String,Object> map : userDataList) {
      Role role = new Role();
      role.setID(safeID("rmm_role_id", map)); 
      role.setName((String)map.get("ro_name"));
      role.setStatus(RoleStatusType.valueOf((String)map.get("ro_status")));

      mapModelObject(role, map, "r");

      roleList.add(role);
    }

    return roleList.get(0);
  }

  @Override
  public Role createRole(Role role) {
    if (role == null) {
      return null;
    }

    StringBuffer sql = new StringBuffer()
    .append(" INSERT INTO rmm_role (ro_name, ro_status, ")
    .append("     ro_add_user_id, ro_add_date, ro_mtc_user_id, ro_mtc_date) ")
    .append(" VALUES (?, ?, ?, ?, ?, ?)");

    int id = jdbcTemplate.update(sql.toString(),
      role.getName(), 
      RoleStatusType.ACTIVE.toString(), 
      "SYSTEM", Calendar.getInstance(),
      "SYSTEM", Calendar.getInstance()
    );

    role.setID(Integer.toString(id));
    return role;
  }

  /**
   * deleteRolesByUserID
   * @param userID
   */
  public void deleteRolesByUserID(String userID) throws SignetNotFoundException {
    StringBuffer sql = new StringBuffer()
      .append(" DELETE FROM rmm_user_role WHERE rmm_user_id = ?");

    int numberOfRows = jdbcTemplate.update(sql.toString(), userID);

    log.info("Delete " + numberOfRows + " row(s) from rmm_user_role.");
  }
}
