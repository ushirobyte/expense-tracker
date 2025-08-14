package com.example.expense_tracker.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RegisterRequest {

    private String email;
    private String password;
    private String fullName;
    private String baseCurrency = "KZT";
}
