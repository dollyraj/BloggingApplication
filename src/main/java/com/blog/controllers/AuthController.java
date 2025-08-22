package com.blog.controllers;

import com.blog.dto.JwtAuthRequest;
import com.blog.dto.UserDto;
import com.blog.models.User;
import com.blog.security.AuthService;
import com.blog.security.CustomUserDetailsService;
import com.blog.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto){
        //save user
        userService.registerNewUser(userDto);
        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> logIn(@RequestBody JwtAuthRequest jwtAuthRequest){

        String status=authService.verify(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
        return ResponseEntity.ok(status);
    }



    @GetMapping("/debug-auth")
    public String debugAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return "No Authentication";

        return "User: " + auth.getName() + ", Authorities: " + auth.getAuthorities();
    }
}
