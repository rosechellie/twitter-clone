package com.twitterclone.repository;

import com.twitterclone.model.Retweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RetweetRepository extends JpaRepository<Retweet, Long> {

    int countByTweetId(Long tweetId);

}
