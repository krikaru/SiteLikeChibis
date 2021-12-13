package com.example.sitelikechibis.service;

import com.example.sitelikechibis.entity.News;
import com.example.sitelikechibis.repo.NewsRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NewsService {
    private final NewsRepo newsRepo;

    public NewsService(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }

    public List<News> findAll() {
        return newsRepo.findAll();
    }

    public News create(News news) {
        news.setCreationDate(LocalDateTime.now());
        return newsRepo.save(news);
    }

    public News update(News newsFromDb, News news) {
        newsFromDb.setText(news.getText());
        return newsRepo.save(newsFromDb);
    }

    public void delete(News news) {
        newsRepo.delete(news);
    }
}
