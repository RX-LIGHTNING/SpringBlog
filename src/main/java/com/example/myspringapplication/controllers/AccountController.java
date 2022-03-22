package com.example.myspringapplication.controllers;

import com.example.myspringapplication.models.Role;
import com.example.myspringapplication.models.User;
import com.example.myspringapplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@Controller
public class AccountController {
    @Autowired
    UserRepo userRepo;

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, @RequestParam(name = "mail") String mail) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setMail(mail);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        // User userFromDB = userRepo.findUserByUsername(user.getUsername());
        userRepo.save(user);
        return "login";
    }
}
