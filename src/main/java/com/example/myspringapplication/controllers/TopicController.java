package com.example.myspringapplication.controllers;

import com.example.myspringapplication.models.Comment;
import com.example.myspringapplication.models.Role;
import com.example.myspringapplication.models.Topic;
import com.example.myspringapplication.models.User;
import com.example.myspringapplication.repo.CommentRepo;
import com.example.myspringapplication.repo.TopicRepo;
import com.example.myspringapplication.repo.UserRepo;
import com.example.myspringapplication.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Objects;
import java.util.Optional;

@Controller
public class TopicController {
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CommentRepo commentRepo;

    @GetMapping("/topic-list")
    public String showTopicList(Model model) {
        Iterable<Topic> topic = topicRepo.findAll();
        model.addAttribute("topic", topic);
        return "topic-list";
    }

    @PostMapping("/topic-add/accept")
    public RedirectView addTopic(@RequestParam(name = "description") String description, @RequestParam(name = "article") String article, Model model) {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        if (Validator.isCorrectTopicLength(description) && Validator.isCorrectTopicArticle(article)) {
            long topicId = topicRepo.save(new Topic(description, article, userRepo.findUserByUsername(username))).getId();
            return new RedirectView("/topic-view?id=" + topicId);
        } else {
            return new RedirectView("/topic-add");
        }
    }

    @GetMapping("/topic-add")
    public String showAddTopic(Model model) {
        return "topic-add";
    }

    @GetMapping("/topic-view")
    public String showTopic(@RequestParam(name = "id") long id, Model model) {
        if (topicRepo.existsById(id)) {
            model.addAttribute("topic", topicRepo.findById(id).get());
            topicRepo.findById(id).get().setViews(topicRepo.findById(id).get().getViews() + 1);
            model.addAttribute("comment", topicRepo.findById(id).get().getComments());
        } else {
            return "/topic-add";
        }
        return "topic-view";
    }

    @GetMapping("/topic-edit")
    public String showTopicEdit(Model model, @RequestParam(name = "id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userRepo.findUserByUsername(login);
        if (user.getRoles().contains(Role.ADMIN) || Objects.equals(topicRepo.findById(id).get()
                .getUser().getUsername(), user.getUsername())) {
            model.addAttribute("topic", topicRepo.findById(id).get());
            return "topic-edit";
        } else {
            return showTopic(id, model);
        }
    }

    @GetMapping("/topic-edit/accept")
    public RedirectView editTopic(@RequestParam(name = "id") long id, @RequestParam(name = "description") String description, @RequestParam(name = "article") String article, Model model) {
        topicRepo.findById(id).get().setArticle(article);
        topicRepo.findById(id).get().setDescription(description);
        return new RedirectView("/topic-list");
    }

    @GetMapping("/topic-delete")
    public RedirectView topicDelete(Model model, @RequestParam(name = "id") long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String login = authentication.getName();
        User user = userRepo.findUserByUsername(login);
        if (user.getRoles().contains(Role.ADMIN)) {
            topicRepo.deleteById(id);
        }
        return new RedirectView("/topic-list");
    }
}
