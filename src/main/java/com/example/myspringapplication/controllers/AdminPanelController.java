package com.example.myspringapplication.controllers;

import com.example.myspringapplication.models.Topic;
import com.example.myspringapplication.models.User;
import com.example.myspringapplication.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class AdminPanelController {
    @Autowired
    UserRepo userRepo;
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
        return "admin-panels/user-edit";
    }
    @GetMapping("/admin-panel/stats")
    public String getAdminStatsPage(Model model) {
        return "admin-panel";
    }
}
