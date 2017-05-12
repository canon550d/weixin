package hb0712.discovery.dao;

import hb0712.discovery.pojo.Article;

import java.util.List;

public interface ArticleDao {
	public List<Article> getArticles();
	public Article getArticle(String id);
	public Article edit(Article article);
	public Article save(Article article);
	public boolean delete(String id);
}
