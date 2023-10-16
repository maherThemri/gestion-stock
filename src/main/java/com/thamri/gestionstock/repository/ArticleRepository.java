package com.thamri.gestionstock.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thamri.gestionstock.model.Article;
@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer>{
	
	Optional<Article> findArticleByCodeArticle(String codeArticle);

	List<Article> findAllByCategoryId(Integer idCategory);

}
