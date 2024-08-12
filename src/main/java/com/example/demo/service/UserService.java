package com.example.demo.service;

import com.example.demo.entityclass.User;
import com.example.demo.mongorepository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void incrementCount(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            user.setCount(user.getCount() + 1);
            userRepository.save(user);
            System.out.println("Updated count for user: " + user.getName() + " to " + user.getCount());
        } else {
            System.out.println("User not found with email: " + email);
        }
    }

    public int getCountByUsername(String email) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return user.getCount();
        } else {
            System.out.println("User not found with email: " + email);
            return -1;
        }
    }
}
