package com.mathis.rankr.services;

import com.mathis.rankr.exceptions.UserNotFoundException;
import com.mathis.rankr.models.enums.FriendshipStatus;
import com.mathis.rankr.models.friend.Friendship;
import com.mathis.rankr.repository.FriendRepository;
import com.mathis.rankr.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendService(FriendRepository friendRepository, UserRepository userRepository) {
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public Friendship createFriendship(Friendship friendship) {
        Optional<Friendship> existingFriendship = friendRepository
                .getActiveFriendshipByUserIdAndFriendId(friendship.getUserId(), friendship.getFriendId());

        if (existingFriendship.isPresent()) {
            if (existingFriendship.get().getStatus() == FriendshipStatus.PENDING) {
                throw new IllegalArgumentException("Friend request is already pending.");
            }
            if (existingFriendship.get().getStatus() == FriendshipStatus.ACCEPTED) {
                throw new IllegalArgumentException("You are already friend with this user.");
            }
        }

        if (userRepository.getUserByUuid(friendship.getFriendId()).isEmpty()) {
            throw new UserNotFoundException("The user doesn't exist.");
        }

        return friendRepository.save(friendship);
    }
}
