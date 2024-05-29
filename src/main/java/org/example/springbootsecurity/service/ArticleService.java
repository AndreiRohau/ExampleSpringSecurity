package org.example.springbootsecurity.service;

import org.example.springbootsecurity.model.Article;
import org.example.springbootsecurity.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ArticleService {
    @Autowired
    private ArticleRepository repository;

    public Article findArticleByTitle(String title) {
        return repository.findArticleByTitle(title).orElse(null);
    }

    public void saveArticle(Article article) {
        repository.save(article);
    }

    public void saveAllArticle(List<Article> article) {
        repository.saveAll(article);
    }

    public void deleteArticle(Article article) {
        repository.delete(article);
    }
}
