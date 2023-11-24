package com.ayush.reddit.clone.controller;

import com.ayush.reddit.clone.model.Post;
import com.ayush.reddit.clone.pojo.PostRequest;
import com.ayush.reddit.clone.pojo.PostResponse;
import com.ayush.reddit.clone.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostById(postId));
    }

    @GetMapping("/by-user/{userName}")
    public ResponseEntity<List<PostResponse>> getPostsByUser(@PathVariable String userName) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getPostsByUser(userName));
    }

    @GetMapping("/by-subreddit/{subredditId}")
    public List<PostResponse> getPostsBySubreddit(@PathVariable long subredditId) {
        return postService.getPostsBySubReddit(subredditId);
    }
}
