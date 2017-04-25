package hb0712.discovery.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hb0712.discovery.dao.ArticleDao;
import hb0712.discovery.pojo.Article;
import hb0712.discovery.service.ArticleService;

@Service
public class ArticleServiceImpl implements ArticleService {
	@Autowired
	private ArticleDao articleDao;

	public List<Article> getArticles() {
		return articleDao.getArticles();
	}

	public Article getArticle(String id){
		return articleDao.getArticle(id);
	}

	public Article edit(Article article) {
		return articleDao.edit(article);
	}
}
