package com.signet.controller;

import java.util.List;

import com.signet.exceptions.SignetServiceException;
import com.signet.model.user.Role;
import com.signet.model.user.User;
import com.signet.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/v1/rmm/users")
public class UserController {

  @Autowired
  UserService userService;

  @ExceptionHandler(RuntimeException.class) 
  public ResponseEntity<ServiceError> handleExceptions(RuntimeException ex) {
    log.error(ex.getMessage(), ex);
    ServiceError error = new ServiceError(HttpStatus.OK.value(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public User retrieveUserByID(@PathVariable String id) throws SignetServiceException {
    User user = userService.retrieveUserByID(id);
    return user;
  }

  @PostMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<User> searchUsers(@RequestBody User user) throws SignetServiceException {
    List<User> userList = userService.searchUsers(user);
    return userList;
  }

  @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
  public void createUser(@RequestBody User user) throws SignetServiceException {
    userService.createUser(user);
  }

  @GetMapping(value = "/roles/{roleName}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Role retrieveRoleByName(@PathVariable String roleName) throws SignetServiceException {
    return userService.retrieveRoleByName(roleName);
  }

  @PostMapping(value = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
  public void createRole(@RequestBody Role role) throws SignetServiceException {
    userService.createRole(role);
  }
}
