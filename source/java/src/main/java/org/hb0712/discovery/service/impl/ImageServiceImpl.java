package org.hb0712.discovery.service.impl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.hb0712.discovery.dao.ImageDao;
import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.pojo.Export;
import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.service.ImageService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl extends FileServiceImpl implements ImageService{
	private Logger logger = Logger.getLogger(ImageServiceImpl.class);
	
	@Autowired
	public ImageDao imageDao;
	
	public List<Image> list(Page page){
		return imageDao.list(page);
	}
	
	public List<Image> list(Page page, String orderby, Camera camera){
		return imageDao.list(page, orderby, camera);
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
	
	public List<Image> list(String[] ids){
		Integer[] int_ids = new Integer[ids.length];
		for(int i=0;i<ids.length;i++) {
			int_ids[i] = Integer.valueOf(ids[i]);
		}
		return imageDao.list(int_ids);
	}
	public List<Image> listOrderBy(String orderby){
		return imageDao.listOrderBy(orderby);
	}
	
	public List<Image> listRepeat(String camera_id){
		return imageDao.listRepeat(camera_id);
	}
	
	public boolean listRepeatRemove(String[] id){
		return imageDao.listRepeatRemove(id);
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
	
	public Collection<File> scan(File directory){
		
//		File directory = new File(path);
		String[] extensions = new String[] {"jpg", "jpeg", "JPG", "png", "gif", "GIF"};
		Collection<File> listFiles = FileUtils.listFiles(directory, extensions, true);
		
		return listFiles;
	}
	
	public boolean moveFile(Camera camera, Page page, String orderby) {
		String[] data = null;
		
		int p = 1;
		do {
			List<Image> list = imageDao.list(page, orderby, camera);
			
			if(p==1) {
				data = getCachePath(page.getTotal());
			}
			
			if(data!=null) {
				for (int i=0;i<list.size();i++) {
					String folder = camera.getPath();
					if("mobile".equals(camera.getType())) {
						folder = folder.replace("Cache", "WorkSpace\\Mobile");
					} else {
						folder = folder.replace("Cache", "WorkSpace\\Camera");
					}
					int j = (page.getPage() - 1) * page.getPageSize() + i;
					folder = folder + data[j] + "\\";
					
					File newFolder = new File(folder);
					if(!newFolder.exists()){
						newFolder.mkdir();
					}
					
					Image img = list.get(i);
					String newPath = folder + img.getName();
					if(!newPath.equals(img.getPath())) {
						System.out.println((j+1) + " move file from " + img.getPath() + " to " + newPath);
						
						boolean success = new File(img.getPath()).renameTo(new File(newPath));
						if (success) {
							System.out.println(" update database " + img.getPath() + " to " + newPath);
							imageDao.updatePath(newPath, img.getId());
						}
					} else {
						System.out.println((j+1) + " file not move " + img.getId());
					}
				}
				
			}
			
			p = p+1;
			page.setPage(p);
		} while (p-1<page.getLast());
		
		return false;
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

	public boolean save(Export export) {
		return imageDao.save(export);
	}
	
	public List<Map<String, String>> groupbyCamera() {
		return imageDao.groupbyCamera();
	}
	
	public List<Map<String, String>> groupbyCamera2() {
		return imageDao.groupbyCamera2();
	}
	
	public Collection<File> findFileNotInDB(Collection<File> files) {
		Collection<File> notInDb = new ArrayList<File>();
		
		int size = files.size();
		int circle = 1;
		int limit = 100;
		if (size>limit) {
			circle = size/limit;
			if(size%limit!=0) {
				circle = circle+1;
			}
		}
		Integer[] p = new Integer[circle];//数组下标
		for(int i=0;i<circle;i++) {
			if (i<circle-1) {
				p[i] = (i+1)*limit;
			} else {
				p[i] = size;
			}
		}
		for (int i = 0; i < p.length; i++) {
			System.out.println(p[i]);
		}

		int d = 0;
		int i = 0;
		int j = 0;
		String[] paths = new String[p[0]];
		File[] fs = new File[p[0]];
		for (File f:files) {
			
			if (i==p[d]) {
				d = d+1;
				int n = p[d]-(limit*d);
				paths = new String[n];
				fs = new File[n];
				j = 0;
			}
			paths[j] = f.getPath();
			fs[j] = f;
			
			if ((i+1)==p[d]) {
				List<Image> inDB = imageDao.list(paths);
				System.out.println(inDB.size());
				kkk(notInDb, fs, inDB);
			}
			
			i++;
			j++;
		}
		return notInDb;
	}

	//把数据库中不存在的保留，比较files和images，把
	private void kkk(Collection<File> notInDb, File[] files, List<Image> images) {
		for(File f:files) {
			boolean notin = true;
			for(Image img:images) {
				if (f.getPath().equals(img.getPath())) {
					notin = false;
					break;
				}
			}
			if (notin) {
				logger.info("pre insert into db:"+f.getPath());
				notInDb.add(f);
			}
		}
	}
}
