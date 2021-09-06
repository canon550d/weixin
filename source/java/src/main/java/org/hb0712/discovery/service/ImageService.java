package org.hb0712.discovery.service;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.pojo.Image;

public interface ImageService {

	public List<Image> list(Page page);
	public List<Image> list(String date);
	
	public List<Image> listOrderBy(String orderby);
	public Set<String> listTime();
	public Set<String> listYear(Set<String> set);
	
	public Collection<File> scan(String path);
	
	public Image getImage(int id);
	public Image getImage(String id);
	public Image getImageByName(String name);
	public Image getImage(String name, String path);
	
	public boolean update(Image image);
	public boolean save(Image image);
	
	public List<Camera> cameralist();
	public Camera getCamera(String id);
	public Camera getCamera(String maker, String model);
	public boolean update(Camera camera);
	public boolean save(Camera camera);
}
