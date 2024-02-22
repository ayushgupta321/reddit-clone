package com.ayush.reddit.clone.service;

import com.ayush.reddit.clone.exception.PostNotFoundException;
import com.ayush.reddit.clone.model.Post;
import com.ayush.reddit.clone.model.Vote;
import com.ayush.reddit.clone.model.VoteType;
import com.ayush.reddit.clone.pojo.VoteDto;
import com.ayush.reddit.clone.repository.PostRepository;
import com.ayush.reddit.clone.repository.VoteRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class VoteService {

    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final AuthService authService;

    public void vote(VoteDto voteDto){
        Post post = postRepository.findById(voteDto.getPostId()).orElseThrow(()-> new PostNotFoundException("No post found"));
        Optional<Vote> vote = voteRepository.findByPostAndUser(post, authService.getCurrentUser());

        if (vote.isPresent() && vote.get().getVoteType() == voteDto.getVoteType()) {
            log.info("Vote found, same value");
            log.info("Vote Info:" + vote.get().getVoteId() + " " + vote.get().getPost() + " " + vote.get().getVoteType());
            voteRepository.deleteById(vote.get().getVoteId());
            if (voteDto.getVoteType() == VoteType.UPVOTE)
                post.setPostVoteCount(post.getPostVoteCount() -1);
            else
                post.setPostVoteCount(post.getPostVoteCount() + 1);

            postRepository.save(post);
            return;
        } else if (vote.isPresent()) {
            vote.get().setVoteType(voteDto.getVoteType());
            log.info("Vote found, inverse value");
            log.info("Vote Info:" + vote.get().getVoteId() + " " + vote.get().getPost() + " " + vote.get().getVoteType());
            if (voteDto.getVoteType() == VoteType.UPVOTE)
                post.setPostVoteCount(post.getPostVoteCount()  +2);
            else
                post.setPostVoteCount(post.getPostVoteCount()  -2);
        } else {
            log.info("Vote not found");
            vote = Optional.ofNullable(Vote.builder().voteType(voteDto.getVoteType())
                    .user(authService.getCurrentUser())
                    .post(post)
                    .build());
            if (voteDto.getVoteType() == VoteType.UPVOTE)
                post.setPostVoteCount(post.getPostVoteCount()  +1);
            else
                post.setPostVoteCount(post.getPostVoteCount()  -1);
        }
        vote.ifPresent(voteRepository::save);
        postRepository.save(post);

    }
}
