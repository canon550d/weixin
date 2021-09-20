package org.hb0712.discovery.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.pojo.Export;
import org.hb0712.discovery.pojo.Image;

public interface ImageDao{
	public List<Image> list(Page page);
	public List<Image> list(Page page, String orderby, Camera camera);
	public List<Image> list(Integer[] ids);
	public List<Image> listOrderBy(String orderby);
	public List<Date> listTime();
	public List<Image> list(Date satrt, Date end);
	public Image getImage(int id);
	public Image getImageByName(String name);
	public Image getImage(String name, String path);
	public boolean update(Image image);
	public boolean save(Image image);
	public boolean save(Export export);
	
	public List<Map<String, String>> groupbyCamera();
}
