package com.twitterclone.controller;

import com.twitterclone.dto.AuthenticationRequest;
import com.twitterclone.dto.AuthenticationResponse;
import com.twitterclone.dto.UserDTO;
import com.twitterclone.model.User;
import com.twitterclone.security.JwtUtil;
import com.twitterclone.security.CustomUserDetailsService;
import com.twitterclone.service.AuthService;
import com.twitterclone.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private AuthenticationManager authenticationManager;
    private AuthService authService;
    private CustomUserDetailsService customUserDetailsService;
    private JwtUtil jwtUtil;
    private UserService userService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          CustomUserDetailsService customUserDetailsService,
                          JwtUtil jwtUtil, UserService userService,
                          AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response) throws Exception {

        String jwt = authService.login(authenticationRequest);
        System.out.println("login jwt: " + jwt);
        response.addCookie(authService.createCookie("jwt", jwt));

        return ResponseEntity.ok(null);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody User user) {
        UserDTO registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }
}
