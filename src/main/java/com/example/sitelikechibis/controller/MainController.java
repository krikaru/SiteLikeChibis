package com.example.sitelikechibis.controller;

import com.example.sitelikechibis.entity.News;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.Views;
import com.example.sitelikechibis.service.NewsService;
import com.example.sitelikechibis.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
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
    private final UserService userService;
    private final ObjectWriter userWriter;
    private final ObjectWriter newsWriter;

    public MainController(NewsService newsService, UserService userService, ObjectMapper mapper) {
        this.newsService = newsService;
        this.userService = userService;

        ObjectMapper objectMapper = mapper
                .setConfig(mapper.getSerializationConfig());

        this.userWriter = objectMapper
                .writerWithView(Views.FullProfile.class);
        this.newsWriter = objectMapper
                .writerWithView(Views.FullNews.class);
    }

    @GetMapping
    public String main(Model model, @AuthenticationPrincipal User principal) throws JsonProcessingException {
        List<News> news = newsService.findAll();
        String serializedNews = newsWriter.writeValueAsString(news);
        model.addAttribute("news", serializedNews);
        if (principal != null) {
            User userFromDb = userService.findById(principal.getId()).get();
            String serializedProfile = userWriter.writeValueAsString(userFromDb);
            model.addAttribute("principal", serializedProfile);
        } else {
            model.addAttribute("principal", "null");
        }

        return "index";
    }

}
