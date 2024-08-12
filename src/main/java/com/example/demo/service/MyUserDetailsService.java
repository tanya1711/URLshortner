package com.example.demo.service;

import com.example.demo.entityclass.User;
import com.example.demo.mongorepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

   @Autowired
   private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("hellooo "+userRepository.findByEmail(username).getPassword());

        User user =  userRepository.findByEmail(username);

        UserDetails userDetails= org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .build();

        System.out.println(userDetails.getUsername()+" "+userDetails.getPassword()+" "+userDetails.getAuthorities());

        return userDetails;
    }


}


