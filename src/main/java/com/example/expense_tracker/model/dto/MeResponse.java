package com.example.expense_tracker.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MeResponse {
    private Long id;
    private String email;
    private String fullName;
    private String baseCurrency;

}
