package org.hb0712.discovery.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.hb0712.discovery.controller.AdministratorController;
import org.hb0712.discovery.dao.ImageDao;
import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.service.ImageService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService{
	private Logger logger = Logger.getLogger(ImageServiceImpl.class);
	
	@Autowired
	public ImageDao imageDao;
	
	public List<Image> list(){
		return imageDao.list();
	}
	public List<Image> listOrderBy(String orderby){
		return imageDao.listOrderBy(orderby);
	}
	
	public List<Date> listTime() {
		return imageDao.listTime();
	}
	
	public List<Image> list(String date) {
		Date start = null, end = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			start = format.parse(date+" 00:00:00");
			end = format.parse(date+" 23:59:59");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageDao.list(start, end);
	}
	
	public Collection<File> scan(String path){
		
		File directory = new File(path);
		String[] extensions = new String[] {"jpg", "jpeg", "JPG", "png", "gif", "GIF"};
		Collection<File> listFiles = FileUtils.listFiles(directory, extensions, true);
		
		return listFiles;
	}
	
	public Image getImage(int id) {
		return imageDao.getImage(id);
	}
	public Image getImage(String name, String path) {
		return imageDao.getImage(name, path);
	}
	
	public Image getImage(String id) {
		int int_id = Integer.valueOf(id);
		return imageDao.getImage(int_id);
	}
	
	public boolean update(Image image) {
		return imageDao.update(image);
	}
	
	public boolean save(Image image) {
		return imageDao.save(image);
	}
}
