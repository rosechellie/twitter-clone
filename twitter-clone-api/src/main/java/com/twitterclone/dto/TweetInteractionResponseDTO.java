package com.twitterclone.dto;

import java.time.LocalDateTime;

public record TweetInteractionResponseDTO(
        Long tweetId,
        String tweetContent,
        String username,
        LocalDateTime createdAt
) {
}
