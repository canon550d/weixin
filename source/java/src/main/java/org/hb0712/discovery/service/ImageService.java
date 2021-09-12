package org.hb0712.discovery.service;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Image;

public interface ImageService {

	public List<Image> list(Page page);
	
	public boolean update(Image image);
	
	public boolean save(Image image);
	
	public Image getImage(int id);
	
	public Image getImage(String id);
	
	public List<Image> list(String[] ids);
	public List<Image> listOrderBy(String orderby);
	
	
	/*
	 * 扫描用到
	 */
	public Collection<File> scan(String path);
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
}
