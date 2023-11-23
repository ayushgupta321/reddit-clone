package com.ayush.reddit.clone.controller;

import com.ayush.reddit.clone.filter.JwtAuthenticationFilter;
import com.ayush.reddit.clone.pojo.SubredditDto;
import com.ayush.reddit.clone.service.SubredditService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/subreddit")
public class SubredditController {

    private final SubredditService subredditService;

    private static final Logger logger = LoggerFactory.getLogger(SubredditController.class);

    @GetMapping
    public List<SubredditDto> getAllSubreddits() {
        return subredditService.getAll();
    }

    @GetMapping("/{id}")
    public SubredditDto getSubreddit(@PathVariable Long id) {
        return subredditService.getSubreddit(id);
    }

    @PostMapping
    public SubredditDto create(@RequestBody @Valid SubredditDto subredditDto) {
        return subredditService.save(subredditDto);
    }
}
