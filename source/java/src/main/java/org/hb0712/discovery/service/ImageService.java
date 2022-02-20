package org.hb0712.discovery.service;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.pojo.Export;
import org.hb0712.discovery.pojo.Image;

public interface ImageService {

	public List<Image> list(Page page);
	
	public List<Image> list(Page page, String orderby, Camera camera);
	
	public boolean update(Image image);
	
	public boolean save(Image image);
	
	public Image getImage(int id);
	
	public Image getImage(String id);
	
	public List<Image> list(String[] ids);
	public List<Image> listOrderBy(String orderby);
	public List<Image> listRepeat(String camera_id);
	public boolean listRepeatRemove(String[] id);
	/*
	 * 扫描用到
	 */
	public Collection<File> scan(String path);
	public Collection<File> scan(File directory);
	public Image getImage(String name, String path);
	public Image getImageByName(String name);

	/*
	 * 浏览使用的
	 */
	//把日期都拿来
	public Set<String> listTime();
	//把日期运算下，得到年份
	public Set<String> listYear(Set<String> set);
	//获取某一天的所有照片
	public List<Image> list(String date);
	
	public boolean save(Export export);
	
	
	public String[] getCachePath(int listsize);
	
	public boolean makeCache(String source, String target);
	
	public boolean moveFile(Camera camera, Page page, String orderby);
	
	public List<Map<String, String>> groupbyCamera();
	public List<Map<String, String>> groupbyCamera2();
	//把目录中已经存在于数据库中的图片筛掉
	public Collection<File> findFileNotInDB(Collection<File> files);
}
