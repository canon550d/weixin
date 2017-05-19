package hb0712.discovery.controller;

import hb0712.discovery.pojo.Article;
import hb0712.discovery.pojo.Gallery;
import hb0712.discovery.service.ArticleService;
import hb0712.discovery.service.GalleryService;

import java.util.Date;
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
	@Autowired
	private GalleryService galleryService;

	@RequestMapping("/index")
	public String index(Map<String,Object> model,
			HttpServletRequest request, HttpSession httpSession) {
		List<Article> list = articleService.getArticles();
		model.put("articles", list);
		return "index";
	}
	
	@RequestMapping("/add")
	public String add(String title, String description, String content, String tags, String fileName, String thumbnail,
			Map<String,Object> model,
			HttpServletRequest request, HttpSession httpSession){
		
		String id = null;
		if(StringUtils.isNotEmpty(content)){
			Article article = new Article();
			article.setFileName(fileName);
			article.setTitle(title);
			article.setDescription(description);
			article.setContent(content);
			article.setTags(tags);
			article.setThumbnail(thumbnail);
			article.setTime(new Date());
			
			articleService.save(article);
			id = article.getId();
			
			return "redirect:index.aspx";
		}
		Article article = new Article();
		article.setFileName(fileName);
		model.put("article", article);
		
		List<Gallery> list = galleryService.getGallery();
		model.put("galleries", list);
		return "add";
	}
	
	@RequestMapping("/edit")
	public String edit(String id, String title, String description, String content, String tags, String fileName, String thumbnail,
			Map<String,Object> model,
			HttpServletRequest request, HttpSession httpSession){
		if(StringUtils.isNotEmpty(content)){
			Article article = new Article();
			article.setId(id);
			article.setFileName(fileName);
			article.setTitle(title);
			article.setDescription(description);
			article.setContent(content);
			article.setTags(tags);
			article.setThumbnail(thumbnail);
			
			articleService.edit(article);
			return "redirect:index.aspx";
		}
		Article article = articleService.getArticle(id);
		model.put("article", article);
		
		List<Gallery> list = galleryService.getGallery();
		model.put("galleries", list);
		return "edit";
	}
	
	@RequestMapping("/delete")
	public String delete(String id, 
			HttpServletRequest request, HttpSession httpSession){
		articleService.delete(id);
		return "redirect:index.aspx";
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
