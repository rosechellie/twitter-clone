package com.twitterclone.controller;

import com.twitterclone.dto.TweetDTO;
import com.twitterclone.security.JwtUtil;
import com.twitterclone.service.LikeService;
import com.twitterclone.service.RetweetService;
import com.twitterclone.service.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tweets")
public class LikeController {

    private final TweetService tweetService;
    private final RetweetService retweetService;
    private final LikeService likeService;
    private final JwtUtil jwtUtil;

    @Autowired
    public LikeController(TweetService tweetService,
                          RetweetService retweetService,
                          LikeService likeService,
                          JwtUtil jwtUtil) {
        this.tweetService = tweetService;
        this.retweetService = retweetService;
        this.likeService = likeService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/{tweetId}/like")
    public ResponseEntity<TweetDTO> like(@PathVariable Long tweetId) {
        try {
            TweetDTO tweetDto = likeService.like(tweetId);
            return ResponseEntity.ok(tweetDto);
        } catch (Exception e) {
            return ResponseEntity.ok(null);
        }
    }
}
