package com.ayush.reddit.clone.repository;

import com.ayush.reddit.clone.model.Post;
import com.ayush.reddit.clone.model.User;
import com.ayush.reddit.clone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findByPostAndUser(Post post, User currentUser);
}
