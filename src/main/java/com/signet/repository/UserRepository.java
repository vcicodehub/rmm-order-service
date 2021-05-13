package com.signet.repository;

import java.util.List;

import com.signet.model.user.Role;
import com.signet.model.user.User;

public interface UserRepository {
    public User retrieveUserByID(String id);
    public List<User> searchUsers(User user);
    public String createUser(User user);
    public String createUserRole(User user, Role role);

    public Role retrieveRoleByName(String roleName);
}
