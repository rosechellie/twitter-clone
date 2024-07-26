package com.twitterclone.dto;

public record AuthenticationRequest(
        String username,
        String password
) {
}
