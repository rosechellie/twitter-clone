package com.twitterclone.service;

import com.twitterclone.dto.TweetDTO;
import com.twitterclone.model.Tweet;
import com.twitterclone.model.User;
import com.twitterclone.repository.LikeRepository;
import com.twitterclone.repository.RetweetRepository;
import com.twitterclone.repository.TweetRepository;
import com.twitterclone.repository.UserRepository;
import com.twitterclone.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final RetweetRepository retweetRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;
    private final CommonUtils commonUtils;

    @Autowired
    public TweetService(TweetRepository tweetRepository, RetweetRepository retweetRepository,
                        LikeRepository likeRepository, UserRepository userRepository,
                        CommonUtils commonUtils) {
        this.tweetRepository = tweetRepository;
        this.retweetRepository = retweetRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.commonUtils = commonUtils;
    }

    public TweetDTO createTweet(/*TweetDTO tweetDTO*/ String content) throws Exception {
//        Tweet tweet = mapFromDTO(tweetDTO);
        User user = commonUtils.getUser();
        if (user == null) {
            return null;
        }
        Tweet tweet = new Tweet();
        tweet.setUser(user);
        tweet.setContent(content);
        tweet.setCreatedAt(LocalDateTime.now());
        return mapToDTO(tweetRepository.save(tweet));
    }

    public List<TweetDTO> findAll() {
        List<Tweet> tweets = tweetRepository.findAllByOrderByIdDesc();
        return tweets.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<TweetDTO> findTweetsByUser(String username) {
        User user = userRepository.findByUsername(username);
        List<Tweet> tweets = tweetRepository.findByUserOrderByIdDesc(user);
        return tweets.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<TweetDTO> findTweetsByCurrentUser() throws Exception {
        User user = commonUtils.getUser();
        if(user == null)
            return null;

        List<Tweet> tweets = tweetRepository.findByUserOrderByIdDesc(user);
        return tweets.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public int getCountOfLikes(Tweet tweet) {
        return likeRepository.countByTweetId(tweet.getId());
    }

    public int getCountOfRetweets(Tweet tweet) {
        return retweetRepository.countByTweetId(tweet.getId());
    }

    public TweetDTO mapToDTO(Tweet tweet) {
        if(tweet == null) {
            return null;
        }
        return new TweetDTO(tweet.getId(),
                tweet.getUser().getUsername(),
                tweet.getContent(),
                getCountOfLikes(tweet),
                getCountOfRetweets(tweet),
                tweet.getCreatedAt());
    }

    private Tweet mapFromDTO(TweetDTO tweetDto) {
        if(tweetDto == null) {
            return null;
        }
        Tweet tweet = new Tweet();
        tweet.setUser(userRepository.findByUsername(tweetDto.username()));
        tweet.setContent(tweetDto.content());
        return tweet;
    }
}
