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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class CommentController {
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    TopicRepo topicRepo;
    @PostMapping("/comment-add")
    public RedirectView commentAdd(Model model,@RequestParam(name = "text") String text,@RequestParam(name = "topic_id") long topId) {
        if(text.length()>0) {
            Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
            String username = loggedInUser.getName();
            topicRepo.findById(topId).get().getComments().add(commentRepo.save(new Comment(text, userRepo.findUserByUsername(username))));
        }
        return new RedirectView("/topic-view?id="+topId);
    }
    @PostMapping("/comment-delete")
    public RedirectView commentDelete(@RequestParam(name = "id") long id, @RequestParam(name = "top_id") long top_id) {
        topicRepo.findById(top_id).get().getComments().remove(commentRepo.findById(id).get());
        commentRepo.deleteById(id);
        return new RedirectView("/topic-view?id="+top_id);
    }
}
