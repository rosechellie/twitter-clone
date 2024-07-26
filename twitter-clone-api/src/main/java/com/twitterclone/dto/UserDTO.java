package com.twitterclone.dto;

import java.time.LocalDateTime;

public record UserDTO(
        String username,
        String password,
        LocalDateTime createdAt
) {
}
