package com.example.myspringapplication.controllers;

import com.example.myspringapplication.models.Topic;
import com.example.myspringapplication.repo.TopicRepo;
import com.example.myspringapplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/")
    public String home(Model model){
        Iterable<Topic> topic = topicRepo.findAll();
        model.addAttribute("topic",topic);
        return "home";
    }
}
