package com.mathis.rankr.repository;

import com.mathis.rankr.models.friend.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FriendRepository extends JpaRepository<Friendship, Long> {
    @Query("select f from Friendship f where f.userId = :userId AND f.friendId = :friendId AND f.deletedAt Is null")
    Optional<Friendship> getActiveFriendshipByUserIdAndFriendId(
            @Param("userId") UUID userId,
            @Param("friendId") UUID friendId
    );
}
