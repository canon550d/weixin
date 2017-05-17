package hb0712.discovery;

import hb0712.discovery.pojo.Gallery;
import hb0712.discovery.pojo.Image;
import hb0712.discovery.utils.SheetBean;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public class GalleryManage {
	private static GalleryManage manage;
	private List<Gallery> galleries;
	
	public List<Gallery> getGallery(){
		return galleries;
	}
	
	public Gallery getGallery(String id){
		for(Gallery a:galleries){
			if(a.getId().equals(id)){
				return a;
			}
		}
		return null;
	}
	
	public Gallery edit(Gallery g, SheetBean sb){
		Element element = sb.getRoot();
		String id = g.getId();
		Element g_element = sb.getElement("galleries/gallery[id="+id+"]");
		this.setGallery(g, g_element);
		sb.save(element.getDocument());
		return g;
	}
	
	public Gallery save(Gallery g, SheetBean sb){
		String id = sb.getNewId("galleries/gallery/id");
		g.setId(id);
		//新建一个image.x.xml文件
		String imagePath = this.getImagePath(sb, id);
		sb.save(imagePath);
		
		Element element = sb.getRoot().getChild("galleries");
		Element e = this.newGallery(g);
		element.addContent(e);
		sb.save(element.getDocument());
		return g;
	}
	
	public static GalleryManage instence(){
		if(manage == null){
			manage = new GalleryManage();
		}
		return manage;
	}
	
	public GalleryManage build(SheetBean sb){
		Element element = sb.getRoot();
		List nodes = null;
		try {
			nodes = XPath.selectNodes(element, "galleries/gallery");
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		galleries = new ArrayList<Gallery>();
		if(nodes!=null && nodes.size()>0){
			for(int i=0;i<nodes.size();i++){
				Element j = (Element)nodes.get(i);
				
				Gallery g = new Gallery();
				this.setGallery(g, j);
				galleries.add(g);
			}
		}
		

		return this;
	}
	
	private Element newImage(Image i){
		Element image = new Element("image");
		
		image.addContent(new Element("id").setText(i.getId()));
		image.addContent(new Element("path").setText(i.getPath()));
		image.addContent(new Element("type").setText(i.getType()));
		image.addContent(new Element("intro").setText(i.getIntro()));
		image.addContent(new Element("gallery").setText(i.getGallery().getId()));
		return image;
	}
	
	private Element newGallery(Gallery g){
		Element gallery = new Element("gallery");
		
		gallery.addContent(new Element("id").setText(g.getId()));
		gallery.addContent(new Element("name").setText(g.getName()));
		gallery.addContent(new Element("intro").setText(g.getIntro()));
		return gallery;
	}
	
	private void setGallery(Gallery g, Element j){
		g.setId(j.getChild("id").getText());
		g.setName(j.getChild("name").getText());
		g.setIntro(j.getChild("intro").getText());
		g.setImages(new ArrayList<Image>());
	}
	
	private String getNewId(Element element, String path){
		String lastid = null;
		try {
			List ids = XPath.selectNodes(element, path);
			
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
	
	
	
	// 跟图片有关的都要另外放了
	public void getImages(SheetBean sb, String gid){
		String file = this.getImagePath(sb, gid);
		Element element = sb.open(file);
		List nodes = null;
		try {
			nodes = XPath.selectNodes(element, "image");
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		List<Image> images = new ArrayList<Image>();
		if(nodes!=null && nodes.size()>0){
			for(int i=0;i<nodes.size();i++){
				Element j = (Element)nodes.get(i);
				
				Image image = new Image();
				setImage(image, j);
				images.add(image);
			}
		}
		getGallery(gid).setImages(images);
	}
	
	public Image save(Image i, SheetBean sb){
		String gid = i.getGallery().getId();
		String file = this.getImagePath(sb, gid);
		Element element = sb.open(file);
		
		String id = this.getNewId(element, "image/id");
		i.setId(id);
		
		
		Element j = this.newImage(i);
		element.addContent(j);
		sb.save(element.getDocument(), file);
		
		Element galleries = sb.getRoot().getChild("galleries");
		Element gallery = sb.getElement("galleries/gallery[id="+id+"]");
		gallery.getChild("images").setText(gallery.getChild("images").getText());
		sb.save(galleries.getDocument());
		return i;
	}
	
	private void setImage(Image image, Element j){
		image.setId(j.getChild("id").getText());
		image.setPath(j.getChild("path").getText());
		image.setIntro(j.getChild("intro").getText());
		
//		String galleryIds = j.getChild("gallery").getText();
//		String[] ids = galleryIds.split(",");
//		for(String id:ids){
//			getGallery(id).getImages().add(image);
//		}
	}
	
	private String getImagePath(SheetBean sb, String i){
		String imagePath = sb.getFilePath().replace("Gallery.xml", "image."+i+".xml");
		System.out.println(imagePath);
		return imagePath;
	}
}
