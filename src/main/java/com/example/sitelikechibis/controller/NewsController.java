package com.example.sitelikechibis.controller;

import com.example.sitelikechibis.entity.News;
import com.example.sitelikechibis.entity.User;
import com.example.sitelikechibis.entity.Views;
import com.example.sitelikechibis.entity.dto.UpdatedEntityDto;
import com.example.sitelikechibis.service.NewsService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("api/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping
    @JsonView(Views.FullNews.class)
    public UpdatedEntityDto<News> create(
            @RequestBody @Valid News news,
            @AuthenticationPrincipal User principal
    ) {
        return new UpdatedEntityDto<>(newsService.create(news, principal), null);
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

    @GetMapping("{id}/like")
    @JsonView(Views.FullNews.class)
    public ResponseEntity<News> like(
            @AuthenticationPrincipal User user,
            @PathVariable("id") News news
    ) {

        if (user != null) {
            Set<User> likes = news.getLikes();
            News responseNews = new News();
            if (likes.contains(user)) {
                likes.remove(user);
            } else {
                likes.add(user);
            }
            responseNews.setLikes(likes);
            return new ResponseEntity<>(responseNews, HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }
}
