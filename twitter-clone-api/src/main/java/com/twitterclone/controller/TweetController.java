package com.twitterclone.controller;

import com.twitterclone.dto.TweetDTO;
import com.twitterclone.service.LikeService;
import com.twitterclone.service.RetweetService;
import com.twitterclone.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tweets")
public class TweetController {

    private final TweetService tweetService;
    private final RetweetService retweetService;
    private final LikeService likeService;

    @Autowired
    public TweetController(TweetService tweetService,
                           RetweetService retweetService,
                           LikeService likeService) {
        this.tweetService = tweetService;
        this.retweetService = retweetService;
        this.likeService = likeService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createTweet(
            @RequestBody /*Map<String, String> requestbody*/ TweetRequest tweetRequest) {
        try {
            String content = tweetRequest.getContent();
            TweetDTO createdTweet = tweetService.createTweet(content);
            return ResponseEntity.ok(createdTweet);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<TweetDTO>> getAllTweets() {
        List<TweetDTO> tweets = tweetService.findAll();
        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<TweetDTO>> getTweetsByUsername(@PathVariable String username) {

        List<TweetDTO> tweets = tweetService.findTweetsByUser(username);
        return ResponseEntity.ok(tweets);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getTweetsByCurrentUser() {

        try {
            List<TweetDTO> tweets = tweetService.findTweetsByCurrentUser();
            return ResponseEntity.ok(tweets);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e);
        }
    }
}

class TweetRequest {
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
