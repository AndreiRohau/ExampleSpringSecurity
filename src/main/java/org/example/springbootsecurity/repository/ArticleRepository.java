package org.example.springbootsecurity.repository;

import org.example.springbootsecurity.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("select a from Article a where a.title = :title")
    Optional<Article> findArticleByTitle(@Param("title") String title);
}
