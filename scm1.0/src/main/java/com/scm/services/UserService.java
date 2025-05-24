package com.scm.services;

import java.util.List;
import java.util.Optional;

import com.scm.entities.User;
import com.scm.helpers.ResourceNotFoundException;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user) throws ResourceNotFoundException;

    void deleteUser(String id) throws ResourceNotFoundException;

    boolean isUserExist(String userId);

    boolean isUserExistByEmail(String email);

    User getUserByEmail(String email);

    List<User> getAllUsers();
}
