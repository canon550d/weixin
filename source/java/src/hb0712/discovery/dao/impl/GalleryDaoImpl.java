package hb0712.discovery.dao.impl;

import hb0712.discovery.GalleryManage;
import hb0712.discovery.dao.GalleryDao;
import hb0712.discovery.pojo.Gallery;
import hb0712.discovery.pojo.Image;
import hb0712.discovery.utils.SheetBean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class GalleryDaoImpl implements GalleryDao{
	@Autowired
	@Qualifier("gallerySheetBean")
	private SheetBean sb;

	public List<Gallery> getGallery() {
		GalleryManage manage = GalleryManage.instence().build(sb);
		return manage.getGallery();
	}
	
	public Gallery getGallery(String id){
		GalleryManage manage = GalleryManage.instence().build(sb);
		manage.getImages(sb, id);
		return manage.getGallery(id);
	}
	
	public Gallery save(Gallery g){
		GalleryManage manage = GalleryManage.instence().build(sb);
		manage.save(g, sb);
		return g;
	}
	
	public Image save(Image i){
		GalleryManage manage = GalleryManage.instence().build(sb);
		manage.save(i, sb);
		return i;
	}

	public static void main(String[] args) {
		GalleryDaoImpl dao = new GalleryDaoImpl();
		dao.getGallery();
	}
}
