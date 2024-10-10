package com.mathis.rankr.controllers;

import com.mathis.rankr.exceptions.UserAlreadyExistsException;
import com.mathis.rankr.exceptions.UserNotFoundException;
import com.mathis.rankr.models.User;
import com.mathis.rankr.models.auth.LoginRequest;
import com.mathis.rankr.models.auth.RegisterRequest;
import com.mathis.rankr.models.auth.UpdateUserRequest;
import com.mathis.rankr.models.utils.CustomResponse;
import com.mathis.rankr.services.UserService;
import com.mathis.rankr.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

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
        try {
            registerRequest.setPassword(userService.hashPassword(registerRequest.getPassword()));

            User newUser = new User(registerRequest);
            User savedUser = userService.createUser(newUser);

            return ResponseUtil.body(HttpStatus.CREATED, savedUser);
        } catch (UserAlreadyExistsException e) {
            return ResponseUtil.build(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            User user = userService.getUserByUsername(loginRequest.getUsername());

            user.updateLastLogin();
            userService.saveUser(user);

            return ResponseUtil.body(HttpStatus.OK, user);
        }  catch (BadCredentialsException e) {
            return ResponseUtil.build(HttpStatus.UNAUTHORIZED, "Invalid username or password.");
        } catch (AuthenticationException e) {
            return ResponseUtil.build(HttpStatus.FORBIDDEN, "Access denied.");
        } catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<CustomResponse> logout() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null) {
                SecurityContextHolder.clearContext();
            }
            return ResponseUtil.build(HttpStatus.OK, "You have been logged out successfully.");
        } catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/user/{uuid}")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest, @PathVariable UUID uuid) {
        try {
            User updatedUser = userService.updateUser(uuid, updateUserRequest);
            return ResponseUtil.body(HttpStatus.OK, updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseUtil.build(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UserAlreadyExistsException e) {
            return ResponseUtil.build(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            return ResponseUtil.build(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
