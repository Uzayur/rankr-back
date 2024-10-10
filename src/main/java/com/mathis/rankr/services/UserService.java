package com.mathis.rankr.services;

import com.mathis.rankr.exceptions.UserAlreadyExistsException;
import com.mathis.rankr.exceptions.UserNotFoundException;
import com.mathis.rankr.models.User;
import com.mathis.rankr.models.auth.UpdateUserRequest;
import com.mathis.rankr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UUID getConnectedUserUuid() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = getUserByUsername(username);
        return user.getUuid();
    }

    public String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public User createUser(User user) {
        if (userRepository.getUserByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("User with this username already exists");
        }

        if (userRepository.getUserByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        return userRepository.save(user);
    }

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(UUID uuid, UpdateUserRequest updateUserRequest) {
        User existingUser = userRepository.getUserByUuid(uuid)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Optional<User> userWithSameUsername = userRepository.getUserByUsername(updateUserRequest.getUsername());
        if (userWithSameUsername.isPresent() && !userWithSameUsername.get().getUuid().equals(uuid)) {
            throw new UserAlreadyExistsException("User with this username already exists");
        }

        Optional<User> userWithSameEmail = userRepository.getUserByEmail(updateUserRequest.getEmail());
        if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getUuid().equals(uuid)) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        existingUser.setUsername(updateUserRequest.getUsername());
        existingUser.setFirstname(updateUserRequest.getFirstname());
        existingUser.setLastname(updateUserRequest.getLastname());
        existingUser.setEmail(updateUserRequest.getEmail());
        existingUser.setPassword(hashPassword(updateUserRequest.getPassword()));
        existingUser.setBio(updateUserRequest.getBio());

        return userRepository.save(existingUser);
    }
}
