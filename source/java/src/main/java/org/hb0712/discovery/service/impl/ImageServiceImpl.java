package org.hb0712.discovery.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.hb0712.discovery.dao.ImageDao;
import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Camera;
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
	
	public List<Image> list(Page page){
		return imageDao.list(page);
	}
	public List<Image> listOrderBy(String orderby){
		return imageDao.listOrderBy(orderby);
	}
	
	public Set<String> listTime() {
		List<Date> list = imageDao.listTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Set<String> dates = new TreeSet<String>(new Comparator<String>() {
			public int compare(String o1, String o2) {
				int num=o2.compareTo(o1);
				return num;
			}
		});
		for (Date d:list) {
			String sd = sdf.format(d);
			dates.add(sd);
		}
		return dates;
	}
	
	public Set<String> listYear(Set<String> dates) {
		Set<String> years = new TreeSet<String>(new Comparator<String>() {
			public int compare(String o1, String o2) {
				int num=o2.compareTo(o1);
				return num;
			}
		});
		for (String date:dates) {
			String y = date.substring(0, 4);
			years.add(y);
		}
		return years;
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
	public Image getImageByName(String name) {
		return imageDao.getImageByName(name);
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
	
	public List<Camera> cameralist(){
		return imageDao.cameralist();
	}
	public Camera getCamera(String id) {
		return imageDao.getCamera(id);
	}
	public Camera getCamera(String maker, String model) {
		return imageDao.getCamera(maker, model);
	}
	public boolean update(Camera camera) {
		return imageDao.update(camera);
	}
	public boolean save(Camera camera) {
		return imageDao.save(camera);
	}
}
