package com.mathis.rankr.models.dto;

import com.mathis.rankr.models.User;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private UUID uuid;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String bio;
    private byte[] profilePicture;
    private OffsetDateTime lastLogin;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;

    public static UserDTO fromUser(User user) {
        if (user == null) {
            return null;
        }

        return new UserDTO(
            user.getUuid(),
            user.getUsername(),
            user.getFirstname(),
            user.getLastname(),
            user.getEmail(),
            user.getBio(),
            user.getProfilePicture(),
            user.getLastLogin(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}