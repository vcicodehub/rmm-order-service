package com.signet.service;

import java.util.List;

import com.signet.exceptions.SignetIllegalArgumentException;
import com.signet.exceptions.SignetServiceException;
import com.signet.model.user.Role;
import com.signet.model.user.User;
import com.signet.repository.UserRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

  UserRepository userRepository;

  UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  /**
   * Retrieves a User with the given id.
   * @param id
   * @return User
   * @throws SignetServiceException
   */
//  @Cacheable(cacheNames = "om-user-cache")
  @Override
  public User retrieveUserByID(String id) throws SignetServiceException {
    log.info("retrieveUserByID(" + id + ")");
    return userRepository.retrieveUserByID(id);
  }

  /**
   * Searches all Users using the given User object as
   * a query-by-example object.
   * @param User
   * @return List<User>
   * @throws SignetServiceException
   */
  @Override
  public List<User> searchUsers(User user) {
    return userRepository.searchUsers(user);
  }

  /**
   * Creates a new User with the given roleName.
   * @param User
   * @param roleName
   * @throws SignetServiceException
   */
  @Transactional
  @Override
  public void createUserWithRole(User user, String roleName) throws SignetServiceException {
    if (user == null) {
      throw new SignetIllegalArgumentException("The User is missing.");
    }
    if (ObjectUtils.isEmpty(roleName)) {
      throw new SignetIllegalArgumentException("The Role is missing.");
    }

    User currentUser = this.retrieveUserByID(user.getUserID());
    if (currentUser != null) {
      throw new SignetIllegalArgumentException("The user " + user.getUserID() + " already exists.");
    }

    Role role = this.retrieveRoleByName(roleName);
    if (role == null) {
      throw new SignetIllegalArgumentException("The role name " + roleName + " is invalid.");
    }

    userRepository.createUser(user);
    userRepository.createUserRole(user, role);
  }
  
  /**
   * deleteUser
   * @param userID
   */
  public void deleteUser(String userID) throws SignetServiceException {
    User user = userRepository.retrieveUserByID(userID);
    if (user == null) {
      return;
    }

    userRepository.deleteRolesByUserID(userID);
    userRepository.deleteUser(userID);
  }

  /**
   * Creates a new User with the given User object.  At least one Role
   * is required to create the User.
   * @param User
   * @throws SignetServiceException
   */
  @Transactional
  @Override
  public void createUser(User user) throws SignetServiceException {
    if (user == null) {
      throw new SignetIllegalArgumentException("The User is missing.");
    }

    User currentUser = this.retrieveUserByID(user.getUserID());
    if (currentUser != null) {
      throw new SignetIllegalArgumentException("The user " + user.getUserID() + " already exists.");
    }

    List<Role> roleList = user.getRoles();
    if (roleList == null || roleList.size() == 0) {
      throw new SignetIllegalArgumentException("A new user must have a least one role.");
    }

    userRepository.createUser(user);
    for (Role newRole : roleList) {
      if (newRole.getName() == null) {
        throw new SignetIllegalArgumentException("The role name is missing.");
      }
      Role role = this.retrieveRoleByName(newRole.getName());
      if (role == null) {
        throw new SignetIllegalArgumentException("The role " + newRole.getName() + " is not a valid role name.");
      }
      userRepository.createUserRole(user, role);
    }
  }

//  @Cacheable(cacheNames = "om-role-cache", key = "#roleName")
  @Override
  public Role retrieveRoleByName(String roleName) throws SignetServiceException {
    return userRepository.retrieveRoleByName(roleName);
  }

  public Role createRole(Role role) throws SignetServiceException {
    if (role == null) {
      throw new SignetIllegalArgumentException("The role is missing.");
    }
    if (role.getName() == null || role.getName().isEmpty()) {
      throw new SignetIllegalArgumentException("The role name is missing.");
    }
    return userRepository.createRole(role);
  }

    
}
