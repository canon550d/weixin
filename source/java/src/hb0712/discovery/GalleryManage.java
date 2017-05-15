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
		
		try {
			nodes = XPath.selectNodes(element, "images/image");
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		List<Image> images = new ArrayList<Image>();
		if(nodes!=null && nodes.size()>0){
			for(int i=0;i<nodes.size();i++){
				Element j = (Element)nodes.get(i);
				
				Image image = new Image();
				setGallery(image, j);
			}
		}
		return this;
	}
	
	public void setGallery(Image image, Element j){
		image.setPath(j.getChild("path").getText());
		image.setIntro(j.getChild("intro").getText());
		
		String galleryIds = j.getChild("gallery").getText();
		String[] ids = galleryIds.split(",");
		for(String id:ids){
			getGallery(id).getImages().add(image);
		}
	}
	
	public void setGallery(Gallery g, Element j){
		g.setId(j.getChild("id").getText());
		g.setIntro(j.getChild("intro").getText());
		g.setImages(new ArrayList<Image>());
	}
}
