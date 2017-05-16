package hb0712.discovery.service;

import hb0712.discovery.pojo.Gallery;
import hb0712.discovery.pojo.Image;

import java.util.List;

public interface GalleryService {
	public List<Gallery> getGallery();
	public Gallery getGallery(String id);
	public Gallery save(Gallery g);
	public Image save(Image g);
}
