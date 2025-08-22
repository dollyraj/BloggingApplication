package com.blog.dto;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;  // email only

    private String password;

}