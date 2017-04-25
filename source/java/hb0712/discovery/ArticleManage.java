package hb0712.discovery;

import hb0712.discovery.pojo.Article;
import hb0712.discovery.utils.SheetBean;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateUtils;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

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
			Element j = (Element)node;
			j.getChild("content").setText(article.getContent());
			String name = j.getChild("fileName").getText();
			System.out.println(name);
			sb.save(element.getDocument());
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		return article;
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
	
	private String[] pattern = new String[]{"yyyy-MM-dd HH:mm:ss"};
	private void setValue(Article article, Element j){
		Date t = null;
		try {
			t = DateUtils.parseDate(j.getChild("time").getText(), pattern);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
