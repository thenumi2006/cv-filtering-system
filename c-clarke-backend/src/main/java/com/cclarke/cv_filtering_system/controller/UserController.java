package com.cclarke.cv_filtering_system.controller;

import com.cclarke.cv_filtering_system.model.User;
import com.cclarke.cv_filtering_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setName(userDetails.getName());
        user.setAddress(userDetails.getAddress());
        user.setBirthday(userDetails.getBirthday());
        user.setProfilePhoto(userDetails.getProfilePhoto());


        return userRepository.save(user);
    }
}