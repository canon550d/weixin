package org.hb0712.discovery.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hb0712.discovery.dao.ImageDao;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.pojo.ImageFile;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDaoImpl extends DefaultDaoImpl<Image> implements ImageDao{
	private Logger logger = LoggerFactory.getLogger(ImageDaoImpl.class);
	
	public List<Image> list(Page page){
		return null;
	}
	
	public List<Image> list(Page page, String orderby, Camera camera){
		return null;
	}
	
	public List<Image> listOrderBy(String orderby){
		return null;
	}
	
	public List<Image> listRepeat(String camera_id){
		return null;
	}
	
	public boolean listRepeatRemove(String[] id){
		return false;
	}
	
	public List<Image> list(Date satrt, Date end){
		return null;
	}
	
	public List<Date> listTime(){
		return null;
	}
	
	public Image getImage(int id) {
		return null;
	}
	
	public List<Image> list(Integer[] ids) {
		return null;
	}
	
	public List<Image> list(String[] paths) {
		return null;
	}
	
	public Image getImageByName(String name) {
		return null;
	}
	/*
	 * 查询某目录下某文件
	 */
	public Image getImage(String name, String path) {
		return null;
	}
	
	/*
	 * 查询某目录下某文件
	 */
	public Image getImage(String md5) {
		return null;
	}
	
	public boolean update(Image image){
		return true;
	}
	
	public boolean save(Image image){
		return true;
	}
	
	public boolean updatePath (String path, Integer id){
		return false;
	}
	
	public boolean save(ImageFile file) {
		return true;
	}
	
	public List<Map<String, String>> groupbyCamera() {
		return null;
	}
	
	public List<Map<String, String>> groupbyCamera2() {
		return null;
	}
}
