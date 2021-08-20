package com.signet.service;

import java.util.List;

import com.signet.exceptions.SignetServiceException;
import com.signet.model.user.Role;
import com.signet.model.user.User;

public interface UserService {
    public User retrieveUserByID(String id) throws SignetServiceException;
    public List<User> searchUsers(User user) throws SignetServiceException;
    public void createUser(User user) throws SignetServiceException;
    public void deleteUser(String userID) throws SignetServiceException;
    public void createUserWithRole(User user, String roleName) throws SignetServiceException;

    public Role retrieveRoleByName(String roleName) throws SignetServiceException;
    public Role createRole(Role role) throws SignetServiceException;
}
