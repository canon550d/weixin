package hb0712.discovery.dao;

import hb0712.discovery.pojo.Gallery;

import java.util.List;

public interface GalleryDao {
	public List<Gallery> getGallery();
	
	public Gallery getGallery(String id);
}
