package hb0712.discovery;

import hb0712.discovery.pojo.Article;
import hb0712.discovery.utils.SheetBean;

import java.util.ArrayList;
import java.util.List;

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
	
	public ArticleManage build(SheetBean sb){
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
				article.setFileName(j.getChild("fileName").getText());
				article.setThumbnail(j.getChild("thumbnail").getText());
				article.setTitle(j.getChild("title").getText());
				article.setDescription(j.getChild("description").getText());
				article.setContent(j.getChild("content").getText());
				
				articles.add(article);
			}
		}
		return this;
	}
}
