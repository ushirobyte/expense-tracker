package com.example.expense_tracker.service;

import com.example.expense_tracker.model.dto.LoginRequest;
import com.example.expense_tracker.model.dto.MeResponse;
import com.example.expense_tracker.model.dto.RegisterRequest;
import com.example.expense_tracker.repository.UserRepository;

public interface AuthService {

    public void register(RegisterRequest registerRequest);
    public String login(LoginRequest loginRequest);
    public MeResponse me(String email);

}
