package hb0712.discovery.controller;

import hb0712.discovery.pojo.Article;
import hb0712.discovery.service.ArticleService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ArticleController {
	@Autowired
	private ArticleService articleService;

	@RequestMapping("/index")
	public String index(Map<String,Object> model,
			HttpServletRequest request, HttpSession httpSession) {
		List<Article> list = articleService.getArticles();
		model.put("articles", list);
		return "index";
	}
	
	@RequestMapping("/edit")
	public String edit(String id, String content,
			Map<String,Object> model,
			HttpServletRequest request, HttpSession httpSession){
		if(StringUtils.isNotEmpty(content)){
			Article article = new Article();
			article.setId(id);
			article.setContent(content);
			articleService.edit(article);
		}
		Article article = articleService.getArticle(id);
		model.put("article", article);
		return "edit";
	}
	
	@RequestMapping("/preview")
	public String preview (String id,
			Map<String,Object> model,
			HttpServletRequest request, HttpServletResponse response){
		Article article = articleService.getArticle(id);
		model.put("nav", "column");
		model.put("article", article);
		return "pre.article";
	}
}
