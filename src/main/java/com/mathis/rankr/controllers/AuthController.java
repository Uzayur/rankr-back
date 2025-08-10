package com.mathis.rankr.controllers;

import com.mathis.rankr.models.User;
import com.mathis.rankr.models.auth.LoginRequest;
import com.mathis.rankr.models.auth.RegisterRequest;
import com.mathis.rankr.models.dto.UserDTO;
import com.mathis.rankr.models.utils.CustomResponse;
import com.mathis.rankr.services.UserService;
import com.mathis.rankr.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        User savedUser = userService.createUser(registerRequest);
        UserDTO userDTO = UserDTO.fromUser(savedUser);

        return ResponseUtil.body(HttpStatus.CREATED, userDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody @Valid LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = userService.getUserByUsername(loginRequest.getUsername());

        user.updateLastLogin();
        userService.saveUser(user);

        return ResponseUtil.body(HttpStatus.OK, user);
    }

    @PostMapping("/logout")
    public ResponseEntity<CustomResponse> logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.clearContext();
        }
        return ResponseUtil.build(HttpStatus.OK, "You have been logged out successfully.");
    }
}
