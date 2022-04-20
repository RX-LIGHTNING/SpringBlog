package com.example.myspringapplication.controllers;

import com.example.myspringapplication.models.Role;
import com.example.myspringapplication.models.Topic;
import com.example.myspringapplication.models.User;
import com.example.myspringapplication.repo.TopicRepo;
import com.example.myspringapplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
    @GetMapping("/admin-panel")
    public String getAdminPage(Model model) {
        return "admin-panel";
    }
    @GetMapping("/admin-panel/users")
    public String getAdminUserPage(Model model) {
        Iterable<User> users = userRepo.findAll();
        model.addAttribute("users", users);
        return "admin-panel";
    }
    @GetMapping("/admin-panel/users/edit")
    public String getAdminUserEditPage(@RequestParam(name = "id") long id, Model model) {
        model.addAttribute("user", userRepo.findById(id).get());
        model.addAttribute("roles", Role.values());
        return "admin-panels/user-edit";
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
        model.addAttribute("TodayTopics",topicRepo.findAllByPublishDateBetween(dateBefore, new Date()));
        model.addAttribute("TodayUsers",userRepo.findAllByRegDateBetween(dateBefore, new Date()));
        return "admin-panel";
    }
}
