package com.twitterclone.controller;

import com.twitterclone.dto.AuthenticationRequest;
import com.twitterclone.dto.AuthenticationResponse;
import com.twitterclone.dto.UserDTO;
import com.twitterclone.model.User;
import com.twitterclone.security.JwtUtil;
import com.twitterclone.security.CustomUserDetailsService;
import com.twitterclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService customUserDetailsService;
    private JwtUtil jwtUtil;

    private UserService userService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager,
                           CustomUserDetailsService customUserDetailsService,
                           JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        System.out.println("TEST " + authenticationRequest);
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.username(), authenticationRequest.password()));
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("Incorrect username or password.");
        }

        final UserDetails userDetails = customUserDetailsService.loadUserByUsername(authenticationRequest.username());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        UserDTO registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }
}
