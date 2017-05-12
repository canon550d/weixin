package hb0712.discovery;

import hb0712.discovery.pojo.Article;
import hb0712.discovery.utils.SheetBean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

/*
 * Article是一个Java对象
 * 数据是一个XML对象
 * Manage就是用来把数据在两这个对象之间转换的
 */
public class ArticleManage {

	private static ArticleManage manage;
	private List<Article> articles;
	
	private ArticleManage(){}
	
	public static ArticleManage instence(){
		if(manage == null){
			manage = new ArticleManage();
		}
		return manage;
	}
	
	public List<Article> getArticles(){
		return articles;
	}
	
	public Article getArticle(String id){
		for(Article a:articles){
			if(a.getId().equals(id)){
				return a;
			}
		}
		return null;
	}
	
	public Article edit(Article article, SheetBean sb){
		Element element = sb.getRoot();
		try {
			String id = article.getId();
			Object node = XPath.selectSingleNode(element, "article[id="+id+"]");
			this.setArticle((Element)node, article);
			sb.save(element.getDocument());
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return article;
	}
	
	public Article save(Article article, SheetBean sb){
		Element element = sb.getRoot();
		
		String id = this.getNewId(element);
		article.setId(id);
		
		Element j = this.addArticle();
		this.setArticle(j, article);
		element.addContent(j);
		
		sb.save(element.getDocument());

		return article;
	}
	
	public boolean delete(String id, SheetBean sb){
		Element element = sb.getRoot();
		try {
			
			Object node = XPath.selectSingleNode(element, "article[id="+id+"]");
			element.removeContent((Element)node);
			sb.save(element.getDocument());
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public ArticleManage build(SheetBean sb) {
		Element element = sb.getRoot();
		
		List nodes = null;
		try {
			nodes = XPath.selectNodes(element, "article");
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		
		articles = new ArrayList<Article>();
		if(nodes!=null && nodes.size()>0){
			
			for(int i=0;i<nodes.size();i++){
//				names[i] = ((Element)nodes.get(i)).getText();
				Element j = (Element)nodes.get(i);
				
				Article article = new Article();
				setValue(article, j);
				
				articles.add(article);
			}
		}
		return this;
	}
	
	private String getNewId(Element element){
		String lastid = null;
		try {
			List ids = XPath.selectNodes(element, "article/id");
			
			if(ids!=null && ids.size()>0){
				lastid = ((Element)ids.get(ids.size()-1)).getText();
				Integer newId = Integer.valueOf(lastid) + 1;
				return newId.toString();
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return lastid;
	}
	
	private Element addArticle(){
		Element article = new Element("article");
		
		article.addContent(new Element("id"));
		article.addContent(new Element("fileName"));
		article.addContent(new Element("title"));
		article.addContent(new Element("description"));
		article.addContent(new Element("content"));
		article.addContent(new Element("tags"));
		article.addContent(new Element("thumbnail"));
		article.addContent(new Element("time"));
		return article;
	}
	
	private void setArticle(Element j, Article article){
		j.getChild("id").setText(article.getId());
		j.getChild("fileName").setText(article.getFileName());
		j.getChild("title").setText(article.getTitle());
		j.getChild("description").setText(article.getDescription());
		j.getChild("content").setText(article.getContent());
		j.getChild("tags").setText(article.getTags());
		j.getChild("thumbnail").setText(article.getThumbnail());
		if(article.getTime()!=null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			j.getChild("time").setText(df.format(article.getTime()));
		}
	}
	
	private String[] pattern = new String[]{"yyyy-MM-dd HH:mm:ss"};
	private void setValue(Article article, Element j){
		Date t = null;
		if(j.getChild("time")!=null){
		try {
			t = DateUtils.parseDate(j.getChild("time").getText(), pattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}}
		article.setTime(t);
		article.setId(j.getChild("id").getText());
		article.setFileName(j.getChild("fileName").getText());
		article.setTitle(j.getChild("title").getText());
		article.setDescription(j.getChild("description").getText());
		article.setContent(j.getChild("content").getText());
		article.setTags(j.getChild("tags").getText());
		article.setThumbnail(j.getChild("thumbnail").getText());
	}
}
