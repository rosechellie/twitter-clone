package com.twitterclone.dto;

import java.time.LocalDateTime;

public record TweetDTO(
        Long id,
        String username,
        String content,
        int retweetsCount,
        int likesCount,
        LocalDateTime createdAt
) {
}
