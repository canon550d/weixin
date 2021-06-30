package org.hb0712.discovery.dao;

import java.util.Date;
import java.util.List;

import org.hb0712.discovery.pojo.Image;

public interface ImageDao{
	public List<Image> list();
	public List<Image> listOrderBy(String orderby);
	public List<Date> listTime();
	public List<Image> list(Date satrt, Date end);
	public Image getImage(int id);
	public Image getImage(String name, String path);
	public boolean update(Image image);
	public boolean save(Image image);
}
