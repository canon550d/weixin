package org.hb0712.discovery.service.impl;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;
import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.mapper.ImageMapper;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.service.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl extends FileServiceImpl implements ImageService{
	private Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);
	
	@Autowired
	private FileConfig fileConfig;
	@Autowired
	public ImageMapper imageMapper;
	
	public List<Image> list(Page page){
		Integer total = imageMapper.getCount(null);
		page.setTotal(total);
		
		return imageMapper.getImages(page, null, null);
	}
	
	public List<Image> list(Page page, String orderby, Camera camera){
		Integer total = imageMapper.getCount(camera);
		page.setTotal(total);
		
		return imageMapper.getImages(page, camera, null);
	}
	
	public Image getImage(String id) {
		return imageMapper.getImageById(Integer.valueOf(id));
	}
	
	public Image getImageByMd5(String md5) {
		return imageMapper.getImageByMD5(md5);
	}
	
	public boolean update(Image image) {
		return imageMapper.update(image);
	}
	
	public boolean save(Image image) {
		return imageMapper.save(image);
	}
	
	public List<Image> list(String[] ids){
		Integer[] int_ids = new Integer[ids.length];
		for(int i=0;i<ids.length;i++) {
			int_ids[i] = Integer.valueOf(ids[i]);
		}
		return imageMapper.getImagesByIds(int_ids);
	}
//	public List<Image> listOrderBy(String orderby){
//		return imageDao.listOrderBy(orderby);
//	}
	
	public List<Image> listRepeat(String camera_id){
		List<Image> list = imageMapper.getImagesByCameraId(camera_id);
		
		List<Image> list2 = new ArrayList<Image>();
		for (Image i:list) {
			for (Image i2:list) {
				if (!list2.contains(i)  && !list2.contains(i2)
						&& i.getName().equals(i2.getName()) && i.getTime().compareTo(i2.getTime())==0
						&& !i.getPath().equals(i2.getPath())) {
					list2.add(i);
					list2.add(i2);
					break;
				}
			}
		}
		
		return list2;
	}
	
	public boolean listRepeatRemove(String[] id){
		if (id == null || id.length<1) {
			return false;
		}
		String ids = "";
		for (int i=0;i<id.length;i++) {
			if(i==0) {
				ids = id[i];
			} else {
				ids += ","+id[i];
			}
		}
		
		int i = imageMapper.updateState(ids);

		if(i>0) {
			return true;
		}
		return false;
	}
	
	public Set<String> listTime() {
		List<Date> list = imageMapper.listTime();
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
		String start = date+" 00:00:00";
		String end = date+" 23:59:59";
		if (date.length()==7) {
			start = date+"-01 00:00:00";
			end = date+"-31 23:59:59";
		}
		return imageMapper.getImagesByTimes(start, end);
	}
	
	public Collection<File> scan(String path){
		File directory = new File(path);
		String[] extensions = new String[] {"jpg", "jpeg", "JPG", "png", "gif", "GIF"};
		Collection<File> listFiles = FileUtils.listFiles(directory, extensions, true);
		
		return listFiles;
	}
	
	public Collection<File> scan(File directory){
		
		String[] extensions = new String[] {"jpg", "jpeg", "JPG", "png", "gif", "GIF"};
		Collection<File> listFiles = FileUtils.listFiles(directory, extensions, true);
		
		return listFiles;
	}
	
	
	
//	public Image getImage(int id) {
//		return imageDao.getImage(id);
//	}
	public Image getImageByName(String name) {
		return imageMapper.getImageByName(name);
	}
	public Image getImage(String name, String path) {
		return imageMapper.getImageByNamePath(name, path);
	}

//	public boolean save(ImageFile file) {
//		return imageDao.save(file);
//	}
	
	public List<Map<String, Long>> groupbyCamera() {
		return imageMapper.groupbyCamera();
	}
	
	public List<Map<String, Long>> groupbyCamera2() {
		return imageMapper.groupbyCamera2();
	}
	
	/*
	 * @Deprecated
	 * 
	 * 使用的是每次从文件目录拿100个去数据库查有没有，把没有的记录到返回变量。有个缺点，查数据库需要用文件的path，双条件查询，还不如反过来比较
	 * 
	 */
	public Collection<File> findFileNotInDB(Collection<File> files, String dir) {
		Collection<File> notInDb = new ArrayList<File>();
		
		int size = files.size();System.out.println("files.size:"+size);
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
		// 把302个文件，分3次查询，第一次100个，第二次100个，第三次2个。
		for (int i = 0; i < p.length; i++) {
			System.out.println(p[i]);
		}

		int d = 0;
		int i = 0;
		int j = 0;
		String[] paths = new String[p[0]];//初始化，注意，这里改为存放md5
		String[] names = new String[p[0]];
		File[] fs = new File[p[0]];
		for (File f:files) {
			if (i==p[d]) {
				d = d+1;
				int n = p[d]-(limit*d);
				paths = new String[n];
				names = new String[n];
				fs = new File[n];
				j = 0;
			}
			paths[j] = getImagePath(dir, f);//getFileSavePath(f); DigestUtils.md5Hex(IOUtils.toByteArray(new FileInputStream(file)));
			names[j] = f.getName();
			fs[j] = f;
			
			if ((i+1)==p[d]) {// 看上面的描述，换个说法，第100个查询，第200个查询，第300个查询，第302个查询
				List<Map<String, String>> params = getListParams(paths, names);
				List<Image> inDB = imageMapper.queryImagesByFullpaths(params);
				System.out.println("inDB.size:"+inDB.size());
				setNotInDb(notInDb, fs, inDB, paths);
			}
			
			i++;
			j++;
		}
		System.out.println("notInDb.size:"+notInDb.size());
		return notInDb;
	}
	
	private List<Map<String, String>> getListParams(String[] paths, String[] names){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		for (int i=0;i<paths.length;i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("path", paths[i]);
			map.put("name", names[i]);
			list.add(map);
		}
		return list;
	}

	public String getImagePath(String dir, File file) {
		String file_path = file.getPath();
		String file_name = file.getName();
		String path = file_path;
		
		path = path.replace(dir, "");
		
//		String base_path = fileConfig.getBasePath();
//		if (StringUtils.isNotEmpty(path)) {
//			path = path.replace(base_path, "");
//		}
		
//		if (bucket!=null 
//				&& StringUtils.isNotEmpty(bucket.getPath())) {
//			String bucket_path = bucket.getPath();
//			path = path.replace(bucket_path, "");
//		}
		
//		if (camer!=null 
//				&& StringUtils.isNotEmpty(camer.getPath())) {
//			String camera_path = camer.getPath();
//			path = path.replace(camera_path, "");
//		}
		
		String name = "\\"+file_name;
		path = path.replace(name, "");
		
		return path;
	}
	
	//把数据库中不存在的保留，比较files和images，把
	private void setNotInDb(Collection<File> notInDb, File[] files, List<Image> images, String[] paths) {
		int i = 0;
		for(File f:files) {
			boolean notin = true;
			String path = paths[i];
			logger.info(path + "|"+f.getName());
			for(Image img:images) {
				if (img.getName().equals(f.getName()) && img.getPath().equals(path)) {
					notin = false;//双否定，就是in
					break;
				}
			}
			if (notin) {
				logger.info("pre insert into db:"+f.getPath());
				notInDb.add(f);
			}
			i++;
		}
	}
	
	public boolean moveFiles(Camera camera, Page page, String orderby) {
		StringBuffer sb = new StringBuffer();
		// 初始化
		int p = 1;//page.getPage();
		int pageSize = 100;
		page.setPageSize(pageSize);
		do {
			Integer total = imageMapper.getCount(camera);
			page.setTotal(total);
			
			List<Image> list = imageMapper.getImages(page, camera, orderby);//注意，image没有查询关联对象
			
			System.out.println("page:"+p+",totalPate:"+page.getLast()+",pageSize:"+pageSize + ",size:" + list.size());
			moveFiles(list, camera, sb);
			
			p = p+1;
			page.setPage(p);
		} while (p-1<page.getLast());
		
		try {
			FileUtils.writeStringToFile(new File("D:\\Sya\\Pictures\\test2.txt"), sb.toString(), "GB2312");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	private void moveFiles (List<Image> list, Camera camera, StringBuffer sb) {
		for (int i=0;i<list.size();i++) {
			moveFile(camera, list.get(i), sb);
		}
	}
	
	// move source to target
	private void moveFile(Camera camera, Image img, StringBuffer sb) {
		String basePath = getBasePath(camera, img);//fileConfig.getBasePath()
		String newPath = getNewPath(camera, img);
		//例如 D:\Sya\Pictures\WorkSpace\Camera\LX5\2024\2024-02\2024-02-04
		String targetPath = fileConfig.getBasePath() + basePath + newPath;
		
		File newFolder = new File(targetPath);
		if(!newFolder.exists()){
			System.out.println("newPath: not exist, create "+targetPath);
			newFolder.mkdirs();
		}
		
		String source = fileConfig.getBasePath() + basePath + img.getPath() +"\\"+ img.getName();
		String target = targetPath +"\\"+ img.getName();
		if(!newPath.equals(img.getPath())) {//不同于数据库中旧地址才挪动
			File newFile = new File(target);
			if (newFile.exists()) {
				String name = img.getName();
				if (name.contains(".")) {
					int d = name.indexOf(".");
					name = name.substring(0,d) + ".copy" + name.substring(d);
				} else {
					name = img.getName() + ".copy";
				}
				newFile = new File(targetPath +"\\"+ name);
			}
			
			System.out.println(" move file from [" + source + "] to [" + target+"]\r\n");
			sb.append("move [").append(source).append("] [").append(target).append("]\r\n");
			
			
//				boolean success = new File(workspace + img.getPath()).renameTo(new File(workspace + newPath));
			boolean success = new File(source).renameTo(newFile);
			if (success) {
				System.out.println(" update database " + img.getPath() + " to " + newPath);
				imageMapper.updatePath(newPath, img.getId());
			} else {
				System.out.println(" move file from " + img.getPath() + " to " + newPath + " fail");
			}
		} else {
			System.out.println(" file not move " + img.getId());
		}
	}
	
	/**
	 * 返回相对地址，例如\WorkSpace\Camera\LX5
	 */
	private String getBasePath(Camera camera, Image img) {
		String bucket_path = img.getBucket().getPath();// "\WorkSpace"
		String camera_path = camera.getPath();// "\Camera\LX5"
		return bucket_path + camera_path;
	}
	
	/**
	 * 返回相机目录后的日期地址，例如\2024\2024-01\2024-01-15
	 * @param camera
	 * @param img
	 * @return
	 */
	private String getNewPath(Camera camera, Image img) {
		String date_path = getTime(img.getTime(), camera.getPath());// "\2024\2024-01\2024-01-15"
		return date_path;
	}
	
	public String getTime(Date date, String type) {
		SimpleDateFormat sdf = null;
		sdf = new SimpleDateFormat("\\yyyy\\yyyy-MM\\yyyy-MM-dd");
		return sdf.format(date);
	}
	
//	public String getFileSavePath(File file) {
//		String path = file.getPath();
//		
//		String workspace = fileConfig.getWorkSpace();
//		if (workspace.endsWith("\\")) {
//			workspace = workspace.substring(0, workspace.length()-1);
//		}
//		
//		if (StringUtils.isNotEmpty(path)) {
//			path = path.replace(workspace, "");
//		}
//		return path;
//	}
	
//	public String getCacheSavePath(Image image, int i, Camera camera) {
//		String workspace = fileConfig.getWorkSpace();
//		//"D:\Sya\Pictures\WorkSpace\" + "Camera\5D3" + "\100\" + "K28A0019.JPG"
//		return workspace + camera.getPath() + "\\"+i+"\\" + image.getName();
//	}
}
