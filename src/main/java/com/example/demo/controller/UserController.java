package com.example.demo.controller;

import com.example.demo.entityclass.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value="/signup")
    public User signup(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public User login(@RequestParam String email, @RequestParam String password) {
        User user = userService.findByEmail(email);
        if (user == null || !user.getPassword().equals(password)) {
            // Invalid credentials
            return null;
        }
        return user; // Return the user object on successful login
    }
}
