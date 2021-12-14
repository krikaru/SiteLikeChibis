package com.example.sitelikechibis.controller;

import com.example.sitelikechibis.entity.News;
import com.example.sitelikechibis.service.NewsService;
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
    public String main(Model model) {
        List<News> news = newsService.findAll();
        model.addAttribute("news", news);
        return "index";
    }

}
