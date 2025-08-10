package com.mathis.rankr.controllers;

import com.mathis.rankr.models.User;
import com.mathis.rankr.models.auth.UpdateUserRequest;
import com.mathis.rankr.models.dto.UserDTO;
import com.mathis.rankr.services.UserService;
import com.mathis.rankr.utils.ResponseUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping("/{uuid}")
    public ResponseEntity<UserDTO> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest, @PathVariable UUID uuid) {
        User updatedUser = userService.updateUser(uuid, updateUserRequest);
        UserDTO userDTO = UserDTO.fromUser(updatedUser);

        return ResponseUtil.body(HttpStatus.OK, userDTO);
    }
}
