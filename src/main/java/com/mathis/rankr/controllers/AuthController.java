package com.mathis.rankr.controllers;

import com.mathis.rankr.models.User;
import com.mathis.rankr.models.auth.LoginRequest;
import com.mathis.rankr.models.auth.RegisterRequest;
import com.mathis.rankr.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.OffsetDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }


    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.getUserByUsername(loginRequest.getUsername());

        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid RegisterRequest registerRequest) {
        User newUser = new User();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setFirstname(registerRequest.getFirstname());
        newUser.setLastname(registerRequest.getLastname());
        newUser.setEmail(registerRequest.getEmail());

        String hashedPassword = userService.hashPassword(registerRequest.getPassword());
        newUser.setPassword(hashedPassword);

        newUser.setBio(registerRequest.getBio());

        newUser.setCreatedAt(OffsetDateTime.now());
        newUser.setUpdatedAt(OffsetDateTime.now());

        User savedUser = userService.saveUser(newUser);

        return ResponseEntity.ok(savedUser);
    }
}
