package com.example.myspringapplication.controllers;

import com.example.myspringapplication.models.Role;
import com.example.myspringapplication.models.User;
import com.example.myspringapplication.repo.TopicRepo;
import com.example.myspringapplication.repo.UserRepo;
import com.example.myspringapplication.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    TopicRepo topicRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/profile")
    public String showProfile(Model model, HttpServletRequest request, @RequestParam(name = "username") String username) {
        model.addAttribute("user",userRepo.findUserByUsername(username));
        model.addAttribute("topic",topicRepo.findTopicsByUser(userRepo.findUserByUsername(username)));
        return "profile";
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest request) {
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam(name = "username") String username, @RequestParam(name = "password") String password, @RequestParam(name = "mail") String mail, Model model) {
        User userFromDB = userRepo.findUserByUsername(username);
        if(userFromDB!=null){
            model.addAttribute("error","User with that login already exists");
            return "registration";
        }
        else if(!Validator.isMail(mail)){
            model.addAttribute("error","Incorrect Email");
            return "registration";
        }
        else if(!Validator.isPassword(password)){
            model.addAttribute("error","Incorrect password");
            return "registration";
        }
        else if(!Validator.isUsername(username))
        {
            model.addAttribute("error","Incorrect username");
            return "registration";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setMail(mail);
        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        return "login";
    }
}
