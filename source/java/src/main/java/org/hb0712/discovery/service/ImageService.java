package org.hb0712.discovery.service;

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hb0712.discovery.pojo.Image;

public interface ImageService {

	public List<Image> list();
	public List<Image> list(String date);
	
	public List<Image> listOrderBy(String orderby);
	public List<Date> listTime();
	
	public Collection<File> scan(String path);
	
	public Image getImage(int id);
	public Image getImage(String id);
	public Image getImage(String name, String path);
	
	public boolean update(Image image);
	public boolean save(Image image);
}
