package com.twitterclone.repository;

import com.twitterclone.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    int countByTweetId(Long tweetId);

}
