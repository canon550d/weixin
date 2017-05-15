package hb0712.discovery.service;

import hb0712.discovery.pojo.Gallery;

import java.util.List;

public interface GalleryService {
	public List<Gallery> getGallery();
	public Gallery getGallery(String id);
}
