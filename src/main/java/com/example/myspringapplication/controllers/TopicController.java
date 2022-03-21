package com.example.myspringapplication.controllers;

import com.example.myspringapplication.models.Topic;
import com.example.myspringapplication.repo.TopicRepo;
import com.example.myspringapplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class TopicController {
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/topic-add/accept")
    public String addTopic(@RequestParam(name = "description") String description, @RequestParam(name = "article") String article, Model model) {
        topicRepo.save(new Topic(description, article, userRepo.findById(1L).get()));
        return "topic-add";
    }

    @GetMapping("/topic-add")
    public String showAddTopic(Model model) {
        return "topic-add";
    }

    @GetMapping("/topic-view")
    public String showTopic(@RequestParam(name = "id") long id, Model model) {
        if(topicRepo.existsById(id)) {
            model.addAttribute("topic", topicRepo.findById(id).get());
        }
        else {
            return"/topic-add";
        }
        return "topic-view";
    }
    @GetMapping("/topic-list")
    public String showTopicList(Model model){
        Iterable<Topic> topic = topicRepo.findAll();
        model.addAttribute("topic",topic);
        return "topic-list";
    }
}
