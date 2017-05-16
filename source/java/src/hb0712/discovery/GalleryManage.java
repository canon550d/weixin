package hb0712.discovery;

import hb0712.discovery.pojo.Gallery;
import hb0712.discovery.pojo.Image;
import hb0712.discovery.utils.SheetBean;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
	
	public Gallery save(Gallery g, SheetBean sb){
		String id = sb.getNewId("galleries/gallery/id");
		g.setId(id);
		
		Element element = sb.getRoot().getChild("galleries");
		Element e = this.newGallery(g);
		element.addContent(e);
		sb.save(element.getDocument());
		return g;
	}
	
	public Image save(Image i, SheetBean sb){
		String id = sb.getNewId("images/image/id");
		i.setId(id);
		
		Element element = getImageElement(sb, id).getChild("images");
		Element j = this.newImage(i);
		element.addContent(j);
		sb.save(element.getDocument());
		return i;
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
	
	private void getOOxx(){
		
	}
	
	private String array2string(String[] array, String separator){
		return StringUtils.join(array, separator);
	}
	
	private Element newImage(Image i){
		Element image = new Element("image");
		
		image.addContent(new Element("id").setText(i.getId()));
		image.addContent(new Element("path").setText(i.getPath()));
		image.addContent(new Element("type").setText(i.getType()));
		image.addContent(new Element("intro").setText(i.getIntro()));
		String ids = array2string(i.getGid(), ",");
		image.addContent(new Element("gallery").setText(ids));
		return image;
	}
	
	private Element newGallery(Gallery g){
		Element gallery = new Element("gallery");
		
		gallery.addContent(new Element("id").setText(g.getId()));
		gallery.addContent(new Element("name").setText(g.getName()));
		gallery.addContent(new Element("intro").setText(g.getIntro()));
		return gallery;
	}
	
	private void setGallery(Image image, Element j){
		image.setPath(j.getChild("path").getText());
		image.setIntro(j.getChild("intro").getText());
		
		String galleryIds = j.getChild("gallery").getText();
		String[] ids = galleryIds.split(",");
		for(String id:ids){
			getGallery(id).getImages().add(image);
		}
	}
	
	private void setGallery(Gallery g, Element j){
		g.setId(j.getChild("id").getText());
		g.setName(j.getChild("name").getText());
		g.setIntro(j.getChild("intro").getText());
		g.setImages(new ArrayList<Image>());
	}
	
	private Element getImageElement(SheetBean sb, String i){
		String imagePath = sb.getFilePath().replace("Gallery.xml", "image."+i+".xml");
		return sb.open(imagePath);
	}
}
