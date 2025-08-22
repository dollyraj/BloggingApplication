package com.blog.security;

import com.blog.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.repositories.UserRepo;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserDetails createUser(User user){
        //Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //userAuth.setRole(userAuth.getRole());
        return userRepo.save(user);
    }

    // to load the user from userName
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // loading user from database by username
        User user = this.userRepo.findByEmail(username)
                .orElseThrow(()-> new ResourceNotFoundException("User", "email: "+username, 0));
        // passed '0' because in our custom 'ResourceNotFoundException' 3rd para is long.
        // Later make new exception ad do it(pass proper one).


        return user;
    }

}