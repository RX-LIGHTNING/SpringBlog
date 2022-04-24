package com.example.myspringapplication.controllers;

import com.example.myspringapplication.models.Comment;
import com.example.myspringapplication.models.Role;
import com.example.myspringapplication.models.Topic;
import com.example.myspringapplication.models.User;
import com.example.myspringapplication.repo.CommentRepo;
import com.example.myspringapplication.repo.TopicRepo;
import com.example.myspringapplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Controller
public class AdminPanelController {
    @Autowired
    UserRepo userRepo;
    @Autowired
    TopicRepo topicRepo;
    @Autowired
    CommentRepo commentRepo;
    @GetMapping("/admin-panel")
    public String getAdminPage(Model model) {
        model.addAttribute("mode","null");
        return "admin-panel";
    }
    @GetMapping("/admin-panel/users")
    public String getAdminUserPage(Model model) {
        Iterable<User> users = userRepo.findAll();
        model.addAttribute("mode","users");
        model.addAttribute("users", users);
        return "admin-panel";
    }
    @GetMapping("/admin-panel/users/edit")
    public String getAdminUserEditPage(@RequestParam(name = "id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findUserByUsername(authentication.getName());
        if(user.getRoles().contains(Role.ADMIN)) {
            model.addAttribute("user", userRepo.findById(id).get());
            model.addAttribute("roles", Role.values());
        }
        return "admin-panels/user-edit";
    }
    @GetMapping("/admin-panel/users/delete")
    public String deleteUser(@RequestParam(name = "id") long id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepo.findUserByUsername(authentication.getName());
        if(user.getRoles().contains(Role.CANDELETEUSERS)) {
            userRepo.delete(userRepo.findById(id).get());
        }
        return "redirect:/admin-panel/users";
    }
    @PostMapping("/admin-panel/users/edit/accept")
    public String getAdminUserEditAccept( @RequestParam(value = "selected", required = false)Role[] selected,@RequestParam(name = "username") String Username, @RequestParam(name = "mail") String Mail, @RequestParam(name = "id") long id, Model model) {
        model.addAttribute("user", userRepo.findById(id).get());
        model.addAttribute("roles", Role.values());
        User user = userRepo.findById(id).get();
        Set<Role> targetSet = new HashSet<Role>();
        Collections.addAll(targetSet, selected);
        user.setRoles(targetSet);
        user.setUsername(Username);
        user.setMail(Mail);
        return "admin-panels/user-edit";
    }
    @GetMapping("/admin-panel/stats")
    public String getAdminStatsPage(Model model) throws ParseException {
        Instant now = Instant.now();
        Instant before = now.minus(Duration.ofDays(1));
        Date dateBefore = Date.from(before);
        model.addAttribute("mode","stats");
        model.addAttribute("TodayTopics",topicRepo.findAllByPublishDateBetween(dateBefore, new Date()));
        model.addAttribute("TodayUsers",userRepo.findAllByRegDateBetween(dateBefore, new Date()));
        return "admin-panel";
    }

}
