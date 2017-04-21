package hb0712.discovery;

import hb0712.discovery.pojo.Article;
import hb0712.discovery.utils.SheetBean;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;


public class FileManage {
	private VelocityEngine velocityEngine;
//	private VelocityContext context;
	
	public FileManage(){
		Properties p = new Properties();
		p.setProperty("resource.loader", "class");
		p.setProperty("class.resource.loader.class","org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
		velocityEngine = new VelocityEngine();
		velocityEngine.init(p);
	}
	
	public static void main(String[] args) {
		SheetBean sb = new SheetBean("hb0712\\discovery\\sheet.xml");
		ArticleManage manage = ArticleManage.instence().build(sb);
		List<Article> list_article = manage.getArticles();
		FileManage fileManage = new FileManage();fileManage.save(list_article);
		// 生成所有的文章页面
		for(Article article:list_article){
			fileManage.save(article);
			System.out.println(article.getFileName());
		}
		System.out.println("共计："+sb.getjobs().length);

		fileManage.save("index.vm", "index.html", "index");
		fileManage.save("about.vm", "about.html", "about");
	}
	
	// 文章保存成html
	public void save(Article article){
		VelocityContext context = new VelocityContext();
		context.put("nav", "column");
		context.put("thumbnail", article.getThumbnail());
		context.put("title", article.getTitle());
		context.put("description", article.getDescription());
		context.put("content", article.getContent());
		save("article.vm", article.getFileName(), context);
	}
	
	// 保存文章列表页
	public void save(List<Article> list_article){
		VelocityContext context = new VelocityContext();
		context.put("nav", "column");
		context.put("articles", list_article);
		save("column.vm", "column.html", context);
	}
	
	// 保存首页等
	public void save(String vmTemplate, String htmlPath, String name){
		VelocityContext context = new VelocityContext();
		context.put("nav", name);
		save(vmTemplate, htmlPath, context);
	}
	
	private void save(String vmTemplate, String htmlPath, VelocityContext context){
		String fileName = ConfigManager.getInstance().getVMPath() + vmTemplate;
		String savePath = ConfigManager.getInstance().getSavePath() + htmlPath;
		
		Template velocity_template = velocityEngine.getTemplate(fileName, "utf-8");
		
//		VelocityContext context = new VelocityContext();


		BufferedWriter writer = null;
		try {
			FileOutputStream fos = new FileOutputStream(createFile(savePath));
			writer = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
			velocity_template.merge(context, writer);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private File createFile(String path) {
		if (path == null){
			return null;
		}
		try {
			File afile = new File(path);
			File parent = afile.getAbsoluteFile().getParentFile();
			if (!parent.exists()){
				parent.mkdirs();
			}
			if (parent.canWrite()){
				return afile;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
