package hb0712.discovery.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hb0712.discovery.ArticleManage;
import hb0712.discovery.dao.ArticleDao;
import hb0712.discovery.pojo.Article;
import hb0712.discovery.utils.SheetBean;

@Repository
public class ArticleDaoImpl implements ArticleDao{
	@Autowired
	private SheetBean sb;

	public List<Article> getArticles() {
		ArticleManage manage = ArticleManage.instence().build(sb);
		List<Article> list_article = manage.getArticles();
		return list_article;
	}

	public Article getArticle(String id){
		ArticleManage manage = ArticleManage.instence().build(sb);
		return manage.getArticle(id);
	}
	
	public Article edit(Article article){
		ArticleManage manage = ArticleManage.instence().build(sb);
		manage.edit(article, sb);
		return article;
	}
}
