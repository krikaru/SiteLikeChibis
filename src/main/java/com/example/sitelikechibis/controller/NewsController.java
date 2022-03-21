package com.example.sitelikechibis.controller;

import com.example.sitelikechibis.controller.util.ControllerUtils;
import com.example.sitelikechibis.entity.News;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.Views;
import com.example.sitelikechibis.entity.dto.UpdatedAttributeEntityDto;
import com.example.sitelikechibis.service.NewsService;
import com.example.sitelikechibis.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final UserService userService;

    public NewsController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping
    public List<News> getAllNews() {
        return newsService.findAll();
    }

    @GetMapping("/{id}")
    public News getOneNews(@PathVariable("id") News news) {
        return news;
    }

    @PostMapping
    @JsonView(Views.BaseUserInfo.class)
    public UpdatedAttributeEntityDto<News> create(
            @RequestBody @Valid News news,
            @AuthenticationPrincipal User principal,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return new UpdatedAttributeEntityDto<>(news, null, ControllerUtils.getErrors(bindingResult));
        } else {
            return new UpdatedAttributeEntityDto<>(newsService.create(news, principal), null, null);
        }
    }

    @PutMapping("{id}")
    public News update(
            @PathVariable("id") News newsFromDb,
            @RequestBody News news)
    {
        return newsService.update(newsFromDb, news);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") News news) {
        newsService.delete(news);
    }

}
