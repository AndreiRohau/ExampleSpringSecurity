package org.example.springbootsecurity.controller;

import org.example.springbootsecurity.model.Article;
import org.example.springbootsecurity.model.User;
import org.example.springbootsecurity.service.ArticleService;
import org.example.springbootsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @GetMapping("/get")
    public String get() {
        return "Hello World";
    }

    @GetMapping("/{title}")
    public ResponseEntity<Article> getArticle(@PathVariable String title) {
        Article article = articleService.findArticleByTitle(title);
        if (article != null) {
            return ResponseEntity.ok().body(article);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PostMapping("/{title}")
    public ResponseEntity<Article> createArticle(@PathVariable String title, @RequestParam String text) {
        if (articleService.findArticleByTitle(title) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByName(authentication.getName());

        Article article = new Article();
        article.setTitle(title);
        article.setText(text);

        user.setArticle(article);
        article.setUser(user);
        articleService.saveArticle(article);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

    @PutMapping("/{title}")
    public ResponseEntity<Article> updateArticle(@PathVariable String title, @RequestBody Article editArticle) {
        Article article = articleService.findArticleByTitle(title);
        if (article != null) {
            article.setTitle(editArticle.getTitle());
            article.setText(editArticle.getText());
            articleService.saveArticle(article);
            return ResponseEntity.ok(article);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<Article> deleteArticle(@PathVariable String title) {
        Article article = articleService.findArticleByTitle(title);
        if (article != null) {
            articleService.deleteArticle(article);
            return ResponseEntity.ok(article);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
