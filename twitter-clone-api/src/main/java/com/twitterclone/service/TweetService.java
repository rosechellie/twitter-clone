package com.twitterclone.service;

import com.twitterclone.dto.TweetDTO;
import com.twitterclone.model.Tweet;
import com.twitterclone.repository.LikeRepository;
import com.twitterclone.repository.RetweetRepository;
import com.twitterclone.repository.TweetRepository;
import com.twitterclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TweetService {

    private final TweetRepository tweetRepository;
    private final RetweetRepository retweetRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Autowired
    public TweetService(TweetRepository tweetRepository, RetweetRepository retweetRepository,
                        LikeRepository likeRepository, UserRepository userRepository) {
        this.tweetRepository = tweetRepository;
        this.retweetRepository = retweetRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    public TweetDTO createTweet(TweetDTO tweetDTO) {
        Tweet tweet = mapFromDTO(tweetDTO);
        tweet.setCreatedAt(LocalDateTime.now());
        return mapToDTO(tweetRepository.save(tweet));
    }

    public List<TweetDTO> findAll() {
        List<Tweet> tweets = tweetRepository.findAll();
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
