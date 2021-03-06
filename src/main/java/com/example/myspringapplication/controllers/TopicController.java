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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class TopicController {
    @Autowired
    private TopicRepo topicRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Value("${upload.path}")
    private String uploadPath;
    @GetMapping("/topic-list")
    public String showTopicList(Model model) {
        Iterable<Topic> topic = topicRepo.findAll();
        model.addAttribute("topic", topic);
        return "topic-list";
    }

    @GetMapping("/topic-list/search")
    public String showFilteredTopicList(@RequestParam(name = "search_query") String search, Model model) {
        HashSet<Topic> topic = new HashSet<>();
        topic.addAll(topicRepo.findAllByArticleContainsIgnoreCase(search));
        for (String searchword:search.split(" ")) {
            topic.addAll(topicRepo.retrieveByTag(searchword));
        }
        model.addAttribute("topic", topic);
        return "topic-list";
    }

    @PostMapping("/topic-add/accept")
    public String addTopic(@RequestParam("file") MultipartFile file, @RequestParam(name = "description") String description, @RequestParam(name = "article") String article, @RequestParam(name = "tags") String tags, Model model) throws IOException {
        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
        String username = loggedInUser.getName();
        String filename = "";
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            filename = resultFilename;
        }
        if (Validator.isCorrectTopicLength(description) && Validator.isCorrectTopicArticle(article)) {
            long topicId = topicRepo.save(new Topic(description, article, userRepo.findUserByUsername(username), List.of(tags.split(" ")), filename)).getId();
            return "redirect:/topic-view?id=" + topicId;
        } else {
            model.addAttribute("error", "Incorrect data");
        }
        return showAddTopic(model);
    }

    @GetMapping("/topic-add")
    public String showAddTopic(Model model) {
        return "topic-add"; //redirect:/
    }

    @GetMapping("/topic-view")
    public String showTopic(@RequestParam(name = "id") long id, Model model) {
        if (topicRepo.existsById(id)) {
            System.out.println(topicRepo.findById(id).get().getTags());
            model.addAttribute("topic", topicRepo.findById(id).get());
            topicRepo.findById(id).get().setViews(topicRepo.findById(id).get().getViews() + 1);
            model.addAttribute("comment", commentRepo.findAllByTopic(topicRepo.findById(id).get()));
            model.addAttribute("topicpubdate", new SimpleDateFormat("yyyy.MM.dd").format(topicRepo.findById(id).get().getPublishDate()));
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

    @PostMapping("/topic-edit/accept")
    public RedirectView editTopic(@RequestParam("file") MultipartFile file, @RequestParam(name = "tags") String tags,@RequestParam(name = "id") long id, @RequestParam(name = "description") String description, @RequestParam(name = "article") String article, Model model) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            topicRepo.findById(id).get().setFilename(resultFilename);
        }
        topicRepo.findById(id).get().setTags(List.of(tags.split(" ")));
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
