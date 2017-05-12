package hb0712.discovery.service.impl;

import hb0712.discovery.dao.GalleryDao;
import hb0712.discovery.pojo.Gallery;
import hb0712.discovery.service.GalleryService;

import java.util.List;

public class GalleryServiceImpl implements GalleryService {
	private GalleryDao galleryDao;

	public List<Gallery> getGallery() {
		return galleryDao.getGallery();
	}

}
