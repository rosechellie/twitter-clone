package com.twitterclone.util;

import com.twitterclone.model.User;
import com.twitterclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommonUtils {

    private final UserRepository userRepository;

    public CommonUtils(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String getUsername() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();
            return userDetails.getUsername();
        } else {
            throw new Exception("Authenticated user not found.");
        }
    }

    public User getUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            return userRepository.findByUsername(userDetails.getUsername());

        } else {
            throw new Exception("Authenticated user not found.");
        }
    }

    public Long getUserId() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal();

            User user = userRepository.findByUsername(userDetails.getUsername());
            if (user == null) {
                return null;
            } else {
                return user.getId();
            }
        } else {
            throw new Exception("Authenticated user not found.");
        }
    }
}
