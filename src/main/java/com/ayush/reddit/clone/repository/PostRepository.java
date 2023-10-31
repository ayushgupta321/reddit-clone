package com.ayush.reddit.clone.repository;

import com.ayush.reddit.clone.model.Post;
import com.ayush.reddit.clone.model.Subreddit;
import com.ayush.reddit.clone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findByUser(User user);
}
