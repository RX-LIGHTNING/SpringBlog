package com.example.myspringapplication.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AboutUsController {
    @GetMapping("/aboutus")
    public String home(Model model, HttpServletRequest request){
        model.addAttribute("temp","О нас.");
        return "aboutus";
    }
}
