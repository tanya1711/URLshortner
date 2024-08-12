package com.example.demo.controller;

import com.example.demo.entityclass.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value="/signup")
    public User signup(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    public User login(@RequestBody User loginUser) {
        Optional<User> user = userService.login(loginUser.getEmail(), loginUser.getPassword());
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("Invalid email or password");
        }
    }
}
