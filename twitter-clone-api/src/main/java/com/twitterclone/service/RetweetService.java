package com.twitterclone.service;

import com.twitterclone.dto.TweetDTO;
import com.twitterclone.dto.TweetInteractionDTO;
import com.twitterclone.dto.TweetInteractionEnum;
import com.twitterclone.exception.ResourceNotFoundException;
import com.twitterclone.exception.UserNotFoundException;
import com.twitterclone.model.Retweet;
import com.twitterclone.model.Tweet;
import com.twitterclone.model.User;
import com.twitterclone.repository.RetweetRepository;
import com.twitterclone.repository.TweetRepository;
import com.twitterclone.repository.UserRepository;
import com.twitterclone.util.CommonUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RetweetService {

    private final RetweetRepository retweetRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final TweetService tweetService;
    private final CommonUtils commonUtils;

    public RetweetService(RetweetRepository retweetRepository,
                          TweetRepository tweetRepository,
                          UserRepository userRepository,
                          TweetService tweetService,
                          CommonUtils commonUtils) {
        this.retweetRepository = retweetRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.tweetService = tweetService;
        this.commonUtils = commonUtils;
    }

    public TweetDTO retweet(Long tweetId) throws Exception {

        String username = commonUtils.getUsername();

        Optional<Tweet> tweet = tweetRepository.findById(tweetId);
        User user = userRepository.findByUsername(username);
        if(tweet.isEmpty()) {
            throw new ResourceNotFoundException("The tweet does not exist.");
        }
        if(user == null) {
//            throw new Exception("User does not exist.");
            throw new UserNotFoundException("The username does not exist.");
        }
        Retweet retweet = new Retweet();
        retweet.setTweet(tweet.get());
        retweet.setUser(user);
        retweet.setCreatedAt(LocalDateTime.now());
        retweetRepository.save(retweet);

        TweetDTO tweetDto = new TweetDTO(
                tweet.get().getId(),
                tweet.get().getUser().getUsername(),
                tweet.get().getContent(),
                tweetService.getCountOfRetweets(tweet.get()) + 1,
                tweetService.getCountOfLikes(tweet.get()),
                tweet.get().getCreatedAt()
        );
        return tweetDto;
    }
}
