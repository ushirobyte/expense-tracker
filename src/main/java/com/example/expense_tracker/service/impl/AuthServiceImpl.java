package com.example.expense_tracker.service.impl;

import com.example.expense_tracker.component.JwtUtil;
import com.example.expense_tracker.model.Role;
import com.example.expense_tracker.model.User;
import com.example.expense_tracker.model.dto.LoginRequest;
import com.example.expense_tracker.model.dto.MeResponse;
import com.example.expense_tracker.model.dto.RegisterRequest;
import com.example.expense_tracker.repository.UserRepository;
import com.example.expense_tracker.service.AuthService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void register(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) throw new IllegalArgumentException("Email already used");
        var user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setFullName(registerRequest.getFullName());
        user.setBaseCurrency(registerRequest.getBaseCurrency());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));

        var role = entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :n", Role.class)
                .setParameter("n", "ROLE_USER").getSingleResult();
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public String login(LoginRequest loginRequest) {
        var user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) throw new IllegalArgumentException("Invalid credentials");
        var authorities = user.getRoles().stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();
        return jwtUtil.generateToken(user.getEmail(), authorities);
    }

    @Override
    public MeResponse me(String email) {
        var user = userRepository.findByEmail(email).orElseThrow();
        return new MeResponse(user.getId(), user.getEmail(), user.getFullName(), user.getBaseCurrency());
    }
}
