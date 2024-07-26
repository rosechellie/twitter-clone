package com.twitterclone.service;

import com.twitterclone.dto.TweetDTO;
import com.twitterclone.dto.TweetInteractionDTO;
import com.twitterclone.model.Like;
import com.twitterclone.model.Retweet;
import com.twitterclone.model.Tweet;
import com.twitterclone.model.User;
import com.twitterclone.repository.LikeRepository;
import com.twitterclone.repository.TweetRepository;
import com.twitterclone.repository.UserRepository;
import com.twitterclone.util.CommonUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LikeService {

    private final LikeRepository likeRepository;
    private final TweetRepository tweetRepository;
    private final UserRepository userRepository;
    private final TweetService tweetService;
    private final CommonUtils commonUtils;

    public LikeService(LikeRepository likeRepository,
                       TweetRepository tweetRepository,
                       UserRepository userRepository,
                       TweetService tweetService,
                       CommonUtils commonUtils) {
        this.likeRepository = likeRepository;
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.tweetService = tweetService;
        this.commonUtils = commonUtils;
    }

    public TweetDTO like(Long tweetId) throws Exception {

        String username = commonUtils.getUsername();

        Optional<Tweet> tweet = tweetRepository.findById(tweetId);
        User user = userRepository.findByUsername(username);
        if(tweet.isEmpty()) {
//            throw new Exception("Tweet does not exist.");
            return null;
        }
        if(user == null) {
//            throw new Exception("User does not exist.");
            return null;
        }
        Like like = new Like();
        like.setTweet(tweet.get());
        like.setUser(user);
        like.setCreatedAt(LocalDateTime.now());
        likeRepository.save(like);

        TweetDTO tweetDto = new TweetDTO(
                tweet.get().getId(),
                tweet.get().getUser().getUsername(),
                tweet.get().getContent(),
                tweetService.getCountOfRetweets(tweet.get()),
                tweetService.getCountOfLikes(tweet.get()) + 1,
                tweet.get().getCreatedAt()
        );
        return tweetDto;
    }
}
