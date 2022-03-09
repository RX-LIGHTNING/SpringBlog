package com.example.myspringapplication.controllers;

import com.example.myspringapplication.models.Topic;
import com.example.myspringapplication.repo.TopicRepo;
import com.example.myspringapplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TopicController {
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private UserRepo userRepo;
    @GetMapping("/topic-add/accept")
    public String addTopic(@RequestParam(name = "description") String description, @RequestParam(name = "article") String article, Model model){
        topicRepo.save(new Topic(description, article, userRepo.findById(1L).get()));
        return "topic-add";
    }
    @GetMapping("/topic-add")
    public String showAddTopic(Model model){
        System.out.println(1);
        return "topic-add";
    }
}
