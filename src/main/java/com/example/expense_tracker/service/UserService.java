package com.example.expense_tracker.service;

import com.example.expense_tracker.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> getAllUsers();
    public Optional<User> getUserByEmail(String email);
    public User saveUser(User user);

}
