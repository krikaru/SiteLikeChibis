package com.example.sitelikechibis.controller;

import com.example.sitelikechibis.entity.News;
import com.example.sitelikechibis.service.NewsService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
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
    public News create(@RequestBody News news) {
        return newsService.create(news);
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
