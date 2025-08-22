package com.blog.security;

import com.blog.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenHelper jwtTokenHelper;

    public String verify(String username,String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username,password)
        );

        if (authentication.isAuthenticated()) {
            System.out.println("User is authenticated");
            return jwtTokenHelper.generateToken(username);
        } else {
            throw new RuntimeException("Invalid credentials");
        }
    }
}

