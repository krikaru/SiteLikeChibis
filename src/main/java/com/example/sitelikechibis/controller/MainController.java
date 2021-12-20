package com.example.sitelikechibis.controller;

import com.example.sitelikechibis.entity.News;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.service.NewsService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {
    private final NewsService newsService;

    public MainController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User user) {
        List<News> news = newsService.findAll();
        model.addAttribute("news", news);
        if (user == null) {
            model.addAttribute("username", "Гость");
        } else {
            model.addAttribute("username", user.getUsername());
        }

        return "index";
    }

}
