package hb0712.discovery.service.impl;

import hb0712.discovery.dao.GalleryDao;
import hb0712.discovery.dao.ImageDao;
import hb0712.discovery.pojo.Gallery;
import hb0712.discovery.pojo.Image;
import hb0712.discovery.service.GalleryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GalleryServiceImpl implements GalleryService {
	@Autowired
	private GalleryDao galleryDao;
	@Autowired
	private ImageDao imageDao;
	// 总分总结构，小学老师教的，Service是总，Dao是分，Manage是总

	public List<Gallery> getGallery() {
		return galleryDao.getGallery();
	}

	public Gallery getGallery(String id){
		Gallery gallery = galleryDao.getGallery(id);
		imageDao.getImages(id);
		return gallery;
	}
	
	public Gallery save(Gallery g){
		return galleryDao.save(g);
	}
	
	public Gallery edit(Gallery g){
		return galleryDao.edit(g);
	}
	
	public boolean delete(String gid, String id){
		return imageDao.delete(gid, id);
	}
	
	public Image getImage(String gid, String id){
		return imageDao.getImage(gid, id);
	}
	public Image save(Image i){
		return galleryDao.save(i);
	}

	public Image edit(Image i) {
		return imageDao.edit(i);
	}
}
