package com.example.gogot.controller;

import com.example.gogot.domain.Role;
import com.example.gogot.domain.User;
import com.example.gogot.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    //регистрация пользователя
    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model ) {
        User userFromDb = userRepository.findByUsername(user.getUsername());
        if (userFromDb != null) {
            model.put("message", "User already");
            return "registration";
        }

        user.setActive(true);
        user.setRoles((Collections.singleton(Role.USER)));
        userRepository.save(user);

        return "redirect:/login";
    }
}
