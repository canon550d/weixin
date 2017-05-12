package hb0712.discovery.service.impl;

import hb0712.discovery.dao.GalleryDao;
import hb0712.discovery.pojo.Gallery;
import hb0712.discovery.service.GalleryService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GalleryServiceImpl implements GalleryService {
	@Autowired
	private GalleryDao galleryDao;

	public List<Gallery> getGallery() {
		return galleryDao.getGallery();
	}

}
