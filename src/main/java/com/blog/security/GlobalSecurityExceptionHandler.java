package com.blog.security;

import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalSecurityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Forbidden");
    }

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<?> handleAuth(AuthenticationException ex) {
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body("Unauthorized");
//    }
}
