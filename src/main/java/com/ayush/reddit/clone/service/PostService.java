package com.ayush.reddit.clone.service;

import com.ayush.reddit.clone.exception.PostNotFoundException;
import com.ayush.reddit.clone.exception.SubredditNotFoundException;
import com.ayush.reddit.clone.exception.UserNotFoundException;
import com.ayush.reddit.clone.model.Post;
import com.ayush.reddit.clone.model.Subreddit;
import com.ayush.reddit.clone.model.User;
import com.ayush.reddit.clone.pojo.PostRequest;
import com.ayush.reddit.clone.pojo.PostResponse;
import com.ayush.reddit.clone.repository.PostRepository;
import com.ayush.reddit.clone.repository.SubredditRepository;
import com.ayush.reddit.clone.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final AuthService authService;
    private final UserRepository userRepository;

    public void save(PostRequest postRequest) {
        Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(() -> new SubredditNotFoundException("SubReddit not found"));
        postRepository.save(postRequestToPostMapper(postRequest, subreddit, authService.getCurrentUser()));
    }

    public PostResponse getPostById(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException("Post Not found"));
        return postToPostResponseMapper(post);
    }

    public List<PostResponse> getPostsByUser(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(() -> new UserNotFoundException("No User found"));
        List<Post> posts = postRepository.findByUser(user);
        return posts.stream().map(this::postToPostResponseMapper).toList();
    }

    public List<PostResponse> getPostsBySubReddit(long subredditId) {
        Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(() -> new SubredditNotFoundException("SubReddit not found"));
        List<Post> posts = postRepository.findAllBySubreddit(subreddit);
        return posts.stream().map(this::postToPostResponseMapper).toList();
    }

    public List<PostResponse> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().map(this::postToPostResponseMapper).toList();
    }

    public PostResponse postToPostResponseMapper(Post post){
        return PostResponse.builder()
                .postName(post.getPostName())
                .url(post.getPostUrl())
                .description(post.getPostDescription())
                .subredditName(post.getSubreddit().getName())
                .userName(post.getUser().getUsername())
                .postName(post.getPostName())
                .id(post.getPostId())
                .build();
    }

    public Post postRequestToPostMapper(PostRequest postRequest, Subreddit subreddit, User user) {
        return Post.builder()
                .postName(postRequest.getPostName())
                .postDescription(postRequest.getDescription())
                .postUrl(postRequest.getUrl())
                .postId(postRequest.getPostId())
                .postVoteCount(0)
                .subreddit(subreddit)
                .user(user)
                .createdDate(Instant.now())
                .build();
    }
}
