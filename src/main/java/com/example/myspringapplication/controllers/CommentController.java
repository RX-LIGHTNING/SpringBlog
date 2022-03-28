package com.example.myspringapplication.controllers;

import com.example.myspringapplication.models.Comment;
import com.example.myspringapplication.models.Topic;
import com.example.myspringapplication.repo.CommentRepo;
import com.example.myspringapplication.repo.TopicRepo;
import com.example.myspringapplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommentController {
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    TopicRepo topicRepo;
    @PostMapping("/comment-add")
    public String commentAdd(Model model,@RequestParam(name = "text") String text,@RequestParam(name = "topic_id") long topId) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        System.out.println(1);
        commentRepo.save(new Comment(text,userRepo.findUserByUsername(username),topicRepo.findById(topId).get()));
        return "topic-list";
    }
}
