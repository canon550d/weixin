package org.hb0712.discovery.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Bucket;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.pojo.Label;
import org.hb0712.discovery.service.AlbumService;
import org.hb0712.discovery.service.BucketService;
import org.hb0712.discovery.service.CameraService;
import org.hb0712.discovery.service.ImageService;
import org.hb0712.discovery.service.LabelService;
import org.hb0712.discovery.service.impl.CommonServiceImpl;
import org.hb0712.discovery.service.impl.EntityService;
import org.hb0712.discovery.service.impl.FileConfig;
import org.hb0712.discovery.service.impl.FileServiceImpl.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

@Controller
@RequestMapping("/admin/image")
public class ImageController {
	private Logger logger = LoggerFactory.getLogger(ImageController.class);
	
	@Autowired
	private ImageService imageService;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private LabelService labelService;
	@Autowired
	private CameraService cameraService;
	@Autowired
	private BucketService bucketService;
	@Autowired
	private EntityService entityService;
	@Autowired
	private FileConfig fileConfig;
	
	@ResponseBody
	@RequestMapping("/preView")
	public byte[] preView(Map<String,Object> model,
			String id,
			HttpServletRequest request) throws IOException {
		Image image = imageService.getImage(id);
		String imgpath = null;
		if(image.getFilesIsNotEmpty()) {
			imgpath = image.getFiles().get(0).getPath();
		} else {
			imgpath = image.getPath();
		}
		Resource resource = new FileSystemResource(imgpath);
		byte[] fileData = FileCopyUtils.copyToByteArray(resource.getInputStream());
		return fileData;
	}
	
	@ResponseBody
	@RequestMapping("/preView2")
	public byte[] preView2(Map<String,Object> model,
			String path, String bucket,
			HttpServletRequest request) throws IOException {
		path = new String(path.getBytes("ISO8859-1"), "UTF-8");
		
		String image_path = URLDecoder.decode(path, "UTF-8");
		image_path = fileConfig.getBasePath() + image_path;
		
		Resource resource = new FileSystemResource(image_path);
		byte[] fileData = FileCopyUtils.copyToByteArray(resource.getInputStream());
		return fileData;
		
	}
	
	// checked
	@RequestMapping("/cache")
	public String cache(Map<String,Object> model,
			Page page, String id, String cover,
			HttpServletRequest request) {
		if ("GET".equals(request.getMethod())) {
			
			String orderby = "time";
			Camera camera = cameraService.getCamera(id);
			
			int p = 1;
			page.setPage(p);
			do {
				List<Image> list = imageService.list(page, orderby, camera);
				
				for (int i=0;i<list.size();i++) {
					Image img = list.get(i);
					String cache = getImageCacheTarget (camera, img);
					File file = new File(cache);
					if(!file.exists() || "cover".equals(cover)) {
						file.getParentFile().mkdirs();

						String f_path = getImagePath(camera, img);//例如
						System.out.println("make file [" + f_path + "] to cache [" + cache + "]");
						boolean result = imageService.makeCache(f_path, cache);
						if(result) {
//							String cache_save = getChchePath(dirName, data[dindex], img.getName());
//							img.setCache(cache_save);System.out.println("file:"+cache_save);
//							imageComponent.update(img);
						}
						//System.out.println("file:"+(dindex+1)+"|path:"+cache);
					}
				}
				
				p = p+1;
				page.setPage(p);
				
			} while (p-1<page.getLast());
			
		}
		
		return "/admin/image/cache";
	}
	
	//整理：对已经扫描到数据库中的照片按照拍摄时间排列，并按100个照片存放到文件夹
	@RequestMapping("/move")
	public String move(Map<String,Object> model,
			Page page, String id,
			HttpServletRequest request) {
		if ("GET".equals(request.getMethod())) {
			
			String orderby = "time";
			Camera camera = cameraService.getCamera(id);
			
			imageService.moveFiles(camera, page, orderby);
		}
		return "/admin/image/cache";
	}
	
	@RequestMapping("/repeat")
	public String repeat(Map<String,Object> model,
			String[] id, String camera_id,
			HttpServletRequest request) {
		if ("GET".equals(request.getMethod())) {
			List<Image> list = imageService.listRepeat(camera_id);
			model.put("list", list);
			return "/admin/image/repeat";
		}
		if ("POST".equals(request.getMethod())) {
			imageService.listRepeatRemove(id);
			return "/image/admin_success";
		}
		return "/admin/image/repeat";
	}
	
	@ResponseBody
	@RequestMapping("/list")
	public String list(Page page,HttpServletRequest request) throws UnsupportedEncodingException {
		if(page==null) {
			page = new Page();
		}
		List<Image> list = null;
		list = imageService.list(page);
		StringBuffer result = new StringBuffer();
		result.append("{").append("\"code\":\"200\",");
		result.append("\"data\":").append("{");
		
		result.append("\"previous\":").append(page.getPrevious()).append(",");
		result.append("\"page\":").append(page.getPage()).append(",");
		result.append("\"next\":").append(page.getNext()).append(",");
		result.append("\"last\":").append(page.getLast()).append(",");
		result.append("\"pageSize\":").append(page.getPageSize()).append(",");
		result.append("\"total\":").append(page.getTotal()).append(",");
		
		result.append("\"list\":").append("[");
		for (int i=0;i<list.size();i++) {
			result.append("{");
			
			Image image = list.get(i);
			entityService.set(image, result);
			
			result.append("}");
			if (i<list.size()-1)
				result.append(",");
		}
		result.append("]");
		
		result.append("}").append("}");
		return result.toString();
	}
	
	// checked
	@RequestMapping("/index")
	public String index(Map<String,Object> model,
			String orderby, Page page, String camera_id,
			HttpServletRequest request) {
		
		if(page==null) {
			page = new Page();
		}
		if(camera_id==null) {
			camera_id = "0";
		}
		model.put("page", page);
		
		if(orderby==null) {
			orderby = "id";
		}model.put("orderby", orderby);
		
		List<Image> list = null;
		if(StringUtils.isNotEmpty(camera_id) && !"0".equals(camera_id)) {
			model.put("camera_id", camera_id);
			Camera camera = cameraService.getCamera(camera_id);
			list = imageService.list(page, orderby, camera);
		} else {
			list = imageService.list(page);
		}
		model.put("list", list);
		
		List<Camera> cameras = cameraService.list();
		model.put("cameras", cameras);
		
		List<Label> labels = labelService.list();
		model.put("labels", labels);
		
		model.put("camera_id", camera_id);
		return "/admin/image/index";
	}
	
	@RequestMapping("/create")
	public String create(Map<String,Object> model,
			Image image,
			HttpServletRequest request) {
		if("POST".equals(request.getMethod())) {
			logger.info(image.getName() + " path.length:"+image.getPath().length());
			imageService.update(image);
		}
		return "/admin/image/create";
	}
	
	@PostMapping
	@ResponseBody
	@RequestMapping("/read")
	public String read(String id) throws UnsupportedEncodingException {
		StringBuffer result = new StringBuffer();
		result.append("{").append("\"code\":\"200\",");
		result.append("\"data\":").append("{");
		
		Image image = imageService.getImage(id);
		entityService.set(image, result);
		
		result.append("}").append("}");
		return result.toString();
	}
	
	// checked
	@RequestMapping("/edit")
	public String edit(Map<String,Object> model,
			Image image, String id, String export_path, String camera_id,
			HttpServletRequest request) {
		if ("GET".equals(request.getMethod())) {
			Image edit_image = imageService.getImage(id);
			model.put("image", edit_image);
			//logger.info(edit_image.toString());
		} else if ("POST".equals(request.getMethod())) {
			logger.info(id);
			Image edit_image = imageService.getImage(id);
			if(edit_image.getCamera()==null || !edit_image.getCamera().getId().equals(camera_id)) {//修正
				Camera camera = cameraService.getCamera(camera_id);
				edit_image.setCamera(camera);
			}
			if(export_path==null || export_path.length()<1) {
//				Export e = new Export();
//				e.setPath(export_path);
//				e.setImage(edit_image);
//				imageComponent.save(e);
//				edit_image.getExports().add(e);
			} else {
				edit_image.setFiles(null);
			}
			edit_image.setName(image.getName());
			edit_image.setPath(image.getPath());
			edit_image.setCache(image.getCache());
			edit_image.setRate(image.getRate());
			edit_image.setDescription(image.getDescription());
			imageService.update(edit_image);
			return "/image/admin_success";
		}
		return "/admin/image/edit";
	}
	
	@RequestMapping("/rescan")
	public String reScan(Map<String,Object> model,
			String path,
			HttpServletRequest request) {
		if ("GET".equals(request.getMethod())) {
			
		} else if ("POST".equals(request.getMethod())) {
			Collection<File> files = imageService.scan(path);
			
			for(File file:files) {
				Image image = imageService.getImageByName(file.getName());
				if(image!=null) {
					image.setPath(file.getPath());
					imageService.save(image);
				}
			}
		}
		
		return "/admin/image/scan";
	}
	
	@RequestMapping("/scan")
	public String scan(Map<String,Object> model,
			String path, String next,
			HttpServletRequest request) {
		
		if(path==null) {
			model.put("image", "E:\\Sya\\图片\\Image\\2015-01-13\\IMG_7788.JPG");
		}else {
			File child_path = null;
			int n = 0;
			if(next!=null && next.length()>0) {
				n = Integer.valueOf(next);
			}
			File directory = new File(path);
			File[] childs = directory.listFiles();
			for(int i=0;i<childs.length;i++) {
				if (childs[i].isDirectory() && i == n) {
					child_path = childs[i];System.out.println(child_path.getPath());
					next = String.valueOf(i+1);
					break;
				}
			}
			if(n<childs.length-1) {
				model.put("next", next);
				model.put("path", path);
			}
			
			Collection<File> files = imageService.scan(child_path);System.out.println(files.size());
			model.put("files", files);
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (File file : files) {
				//据说Sanselan这个开源库不错，晚点试试

				Map<String, String> metadata = getMetadata(file);
				list.add(metadata);
//				long time = file.lastModified();
//				s.add(new Date(time));
			}
			model.put("list", list);System.out.println(list.size());
		}
		return "/admin/image/scan";
	}
	
	// checked 扫描：对相机的目录扫描，如有数据库中没有的新照片，加入数据库
	@RequestMapping("/scan4camera")
	public String scanForCamera(Map<String,Object> model,
			String id, 
			HttpServletRequest request) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		
		Camera c = cameraService.getCamera(id);
		if (c==null) {
			return "/admin/error";
		}
		String dir = c.getPath();
		if (StringUtils.isEmpty(dir)) {
			return "/admin/error";
		}
		
		List<Bucket> buckets = bucketService.list();
		for (Bucket b:buckets) {
			dir = getCameraDir(b, c);
			if (!new File(dir).exists()) {
				continue;
			}
			
			Collection<File> files = imageService.scan(dir);
			if (files==null || files.size()<1) {
				return "/admin/error";
			}
			
			files = imageService.findFileNotInDB(files, dir);
			
			for(File file:files) {
				Map<String, String> m = getMetadata(file);
				if (m==null || m.get("time")==null || m.get("time").length()<1
						|| m.get("make")==null || m.get("make").length()<1
						|| m.get("model")==null || m.get("model").length()<1) {
					logger.info("not photo:"+file.getPath());
					continue;
				}
				if(!c.getMaker().equals(m.get("make")) || !c.getModel().equals(m.get("model"))) {
					logger.info("照片放错目录位置了:"+file.getPath());
					continue;
				}
				String md5 = DigestUtils.md5Hex(IOUtils.toByteArray(new FileInputStream(file)));
				
				Image image = new Image();
				image.setName(file.getName());
				image.setTime(getTime(m.get("time")));
				image.setBucket(b);
				image.setMd5(md5);
				image.setState(0);
				image.setCamera(c);
				
				
				String path = imageService.getImagePath(dir, file);//(image, file.getPath());
				image.setPath(path);
				
				logger.info("insert into name:"+image.getName());
				imageService.save(image);//insert
			}
		}
		//imageComponent.setNumnames(id); TODO 不要了
		return "/image/admin_success";
	}
	
	@RequestMapping("/savescan")
	public String saveScan(String next, String nextPath, String bucket_id,
			String[] index, String[] name, String[] time, String[] maker, String[] model, String[] description, String[] path,
			HttpServletRequest request) throws UnsupportedEncodingException, FileNotFoundException, IOException {
		int i = 0;
		Image image;String md5;
		String _a , _b , _c, _d = null;
		for(String j:index) {
			_a = name[i];
			_b = path[i];
			_c = time==null||time.length<1?null:time[i];
			_d = description==null||description.length<1?null:description[i];
			File file = new File(_b);
			if (file.exists()) {
				md5 = DigestUtils.md5Hex(IOUtils.toByteArray(new FileInputStream(file)));
				image = imageService.getImageByMd5(md5);
			} else {
				continue;
			}
			if(image==null) {
				image = new Image();
				image.setName(_a);
				image.setTime(getTime(_c));
				image.setPath(_b);
				image.setState(0);
				image.setMd5(md5);
				image.setDescription(_d);
				Bucket bucket = new Bucket();
				bucket.setId(Integer.valueOf(bucket_id));
				image.setBucket(bucket);
//				System.out.println(image);
				Camera camera = cameraService.getCamera(maker[i], model[i]);
				if(camera!=null) {
					image.setCamera(camera);
				}
				imageService.update(image);
			}
			System.out.println(name[i]);
			i++;
		}
		if(nextPath!=null && nextPath.length()>0
				&& next !=null && next.length()>0)
			return "redirect:/admin/image/scan.aspx?path="+URLEncoder.encode(nextPath, "UTF-8")+"&next="+next;
		else
			return "redirect:/admin/image/scan.aspx";
	}
	
	private String getChchePath(String dirName, String index, String name) {
		return "\\" + dirName + "\\" + index + "\\" + name;
	}
	
	//dirName , data[dindex], img.getName()
	private String getImageCachePath(String dirName, String index, String name) {
		return fileConfig.getCachePath() + dirName + "\\" + index + "\\" + name;
	}
	
	private String getImageCacheTarget(Camera camera, Image img) {
		return fileConfig.getCachePath()
				+ Sample.Small.getFixedHeight()
				+ img.getBucket().getPath()
				+ camera.getPath()
				+ img.getPath()
				+ "\\" + img.getName();
	}
	
	private String getImagePath(Camera camera, Image img) {
		/*if (img.getPath().startsWith("\\")) {
			return fileConfig.getWorkSpace() + img.getPath().substring(1);
		}*/

		
		return fileConfig.getBasePath()
				+ img.getBucket().getPath()
				+ camera.getPath()
				+ img.getPath()
				+ "\\" + img.getName();
	}
	
	private String getCameraDir(Bucket bucket,Camera camera) {
		return fileConfig.getBasePath() + bucket.getPath() + camera.getPath();
	}
	
	private Date getTime(String date) {
		if(date==null || date.length()<19)
			return null;
		
		Date d = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			d = format.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return d;
	}
	
	private Map<String, String> getMetadata(File file) {
		Metadata metadata = null;
		try {
			metadata = ImageMetadataReader.readMetadata(file);
		} catch (ImageProcessingException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(metadata != null) {
			Map<String, String> lines = new LinkedHashMap<String, String>();
			
			for (Directory directory : metadata.getDirectories()) {
				//windows图片属性中的'照相机'
				if("ExifIFD0Directory".equalsIgnoreCase( directory.getClass().getSimpleName() )){
					String make = directory.getString(ExifIFD0Directory.TAG_MAKE);
					lines.put("make", make);
					String model = directory.getString(ExifIFD0Directory.TAG_MODEL);
					lines.put("model", model);
				}
				if("ExifSubIFDDirectory".equalsIgnoreCase( directory.getClass().getSimpleName() )){
					String time = directory.getString(ExifSubIFDDirectory.TAG_DATETIME_ORIGINAL);
					if(time!=null && time.length()>0) {
						if (time.indexOf(" ")>-1) {
							int blankIndex = time.indexOf(" ");
							time = time.substring(0, blankIndex).replaceAll(":", "-") + time.substring(blankIndex);
						}
					}
					lines.put("time", time);
				}
			}
			return lines;
		}
		return null;
	}
}
