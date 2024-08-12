package com.example.demo.service;

import com.example.demo.entityclass.User;
import com.example.demo.mongorepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Find user by email
        Optional<User> optionalUser = userRepository.findByEmail(username);

        // If user is not present, throw an exception
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + username));

        // Log the user's password for debugging (optional)
        System.out.println("User found: " + user.getEmail() + " with password: " + user.getPassword());

        // Create UserDetails object
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .build();

        // Log the UserDetails object for debugging (optional)
        System.out.println("UserDetails created: " + userDetails.getUsername() + " " + userDetails.getPassword() + " " + userDetails.getAuthorities());

        return userDetails;
    }
}