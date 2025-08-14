package com.example.expense_tracker.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    private String email;
    private String password;
}
