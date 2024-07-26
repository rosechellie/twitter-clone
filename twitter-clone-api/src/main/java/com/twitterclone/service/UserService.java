package com.twitterclone.service;

import java.util.List;
    import java.time.LocalDateTime;
import java.util.stream.Collectors;

import com.twitterclone.dto.UserDTO;
    import com.twitterclone.model.User;
import com.twitterclone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public UserDTO registerUser(User user) {
        user.setCreatedAt(LocalDateTime.now());
        return mapToDTO(userRepository.save(user));
    }

    public UserDTO findByUsername(String username) {
        return mapToDTO(userRepository.findByUsername(username));
    }

    private UserDTO mapToDTO(User user) {
        if(user == null) {
            return null;
        }
        return new UserDTO(user.getUsername(), user.getPassword(), user.getCreatedAt());
    }
 }
