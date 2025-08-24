package com.example.expense_tracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class RegisterRequest {

    private String email;
    private String password;
    private String fullName;
    private String baseCurrency = "KZT";
}
