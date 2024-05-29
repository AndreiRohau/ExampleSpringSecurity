package org.example.springbootsecurity.config;

import org.example.springbootsecurity.SpringBootSecurityApplication;
import org.example.springbootsecurity.model.Article;
import org.example.springbootsecurity.model.Role;
import org.example.springbootsecurity.model.User;
import org.example.springbootsecurity.service.ArticleService;
import org.example.springbootsecurity.service.RoleService;
import org.example.springbootsecurity.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DatabaseLoading {
    private Logger log = LoggerFactory.getLogger(SpringBootSecurityApplication.class);

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner loadData() {
        return args -> {
            Role userRole = new Role();
            userRole.setName("USER");

            Role adminRole = new Role();
            adminRole.setName("ADMIN");

            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(userRole);
            admin.setRole(adminRole);
            adminRole.setUser(admin);
            userRole.setUser(admin);

            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setRole(userRole);
            userRole.setUser(user);

            roleService.saveRole(userRole);
            roleService.saveRole(adminRole);

            userService.saveUser(admin);
            userService.saveUser(user);

            Article article1 = new Article("Introduction to Spring Boot", "This article explains the basics of Spring Boot.", admin);
            Article article2 = new Article("Advanced Spring Security", "This article dives into advanced features of Spring Security.", admin);
            Article article3 = new Article("Getting Started with REST APIs", "This article provides a beginner's guide to REST APIs.", user);
            Article article4 = new Article("Deploying Spring Boot Applications", "This article covers various deployment strategies for Spring Boot applications.", user);

            articleService.saveAllArticle(List.of(article1, article2, article3, article4));

            userService.getAllUsers().forEach(user3 -> log.info("Preloaded " + user));
        };
    }
}
