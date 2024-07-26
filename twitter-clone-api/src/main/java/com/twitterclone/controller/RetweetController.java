package com.twitterclone.controller;

import com.twitterclone.dto.TweetDTO;
import com.twitterclone.service.LikeService;
import com.twitterclone.service.RetweetService;
import com.twitterclone.service.TweetService;
import com.twitterclone.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class RetweetController {

    private final TweetService tweetService;
    private final RetweetService retweetService;
    private final LikeService likeService;

    @Autowired
    public RetweetController(TweetService tweetService,
                             RetweetService retweetService,
                             LikeService likeService,
                             CommonUtils commonUtils) {
        this.tweetService = tweetService;
        this.retweetService = retweetService;
        this.likeService = likeService;
    }

    @PostMapping("/{tweetId}/retweet")
    public ResponseEntity<TweetDTO> retweet(@PathVariable Long tweetId) {
        try {
            TweetDTO tweetDto = retweetService.retweet(tweetId);
            return ResponseEntity.ok(tweetDto);
        } catch (Exception e) {
            return ResponseEntity.ok(null);
        }
    }
}
