package com.twitterclone.controller;

import com.twitterclone.dto.TweetDTO;
import com.twitterclone.dto.TweetInteractionDTO;
import com.twitterclone.dto.TweetInteractionEnum;
import com.twitterclone.service.LikeService;
import com.twitterclone.service.RetweetService;
import com.twitterclone.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<TweetDTO> createTweet(@RequestBody TweetDTO tweetDto) {
        TweetDTO createdTweet = tweetService.createTweet(tweetDto);
        return ResponseEntity.ok(createdTweet);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TweetDTO>> getAllTweets() {
        List<TweetDTO> tweets = tweetService.findAll();
        return ResponseEntity.ok(tweets);
    }

//    @PostMapping("/{tweetId}/retweet")
//    public ResponseEntity<TweetDTO> retweet(@PathVariable Long tweetId,
//                                            @RequestBody String username) {
//        // TODO get username from token
//        try {
//            TweetDTO tweetDto = retweetService.retweet(tweetId, username);
//            return ResponseEntity.ok(tweetDto);
//        } catch (Exception e) {
//            return ResponseEntity.ok(null);
//        }
//    }
//
//    @PostMapping("/{tweetId}/like")
//    public ResponseEntity<TweetDTO> like(@PathVariable Long tweetId,
//                                            @RequestBody String username) {
//        // TODO get username from token
//        try {
//            TweetDTO tweetDto = likeService.like(tweetId, username);
//            return ResponseEntity.ok(tweetDto);
//        } catch (Exception e) {
//            return ResponseEntity.ok(null);
//        }
//    }
}
