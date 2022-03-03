package com.example.myspringapplication.controllers;

import com.example.myspringapplication.models.Topic;
import com.example.myspringapplication.repo.PhotoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {
    @Autowired
    private PhotoRepo photoRepo;
    @GetMapping("/")
    public String home(Model model, HttpServletRequest request){
//        System.out.println("User with IP:"+request.getRemoteAddr()+" connected to server. session: "+request.getSession().getId()+"\n"+
//        "Page:"+request.getRequestURI());
        Iterable<Topic> photos = photoRepo.findAll();
        model.addAttribute("photos",photos);
        return "home";
    }
}
