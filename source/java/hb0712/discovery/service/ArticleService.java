package hb0712.discovery.service;

import hb0712.discovery.pojo.Article;

import java.util.List;

public interface ArticleService {

	public List<Article> getArticles();
	
	public Article getArticle(String id);
	
	public Article edit(Article article);
}
