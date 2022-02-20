package org.hb0712.discovery.controller;

import java.io.File;
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

import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Album;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.service.AlbumService;
import org.hb0712.discovery.service.CameraService;
import org.hb0712.discovery.service.ImageService;
import org.hb0712.discovery.service.impl.FileConfig;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

@Controller
public class AdministratorController {
	private Logger logger = Logger.getLogger(AdministratorController.class);
	
	@Autowired
	private ImageService imageService;
	@Autowired
	private CameraService cameraService;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private FileConfig fileConfig;

	@ResponseBody
	@RequestMapping("/admin/image/preView")
	public byte[] imagePreView(Map<String,Object> model,
			String id,
			HttpServletRequest request) throws IOException {
		Image image = imageService.getImage(Integer.valueOf(id));
		String imgpath = null;
		if(image.getExportsIsNotEmpty()) {
			imgpath = image.getExports().get(0).getPath();
		} else {
			imgpath = image.getPath();
		}
		Resource resource = new FileSystemResource(imgpath);
		byte[] fileData = FileCopyUtils.copyToByteArray(resource.getInputStream());
		return fileData;
	}
	
	@ResponseBody
	@RequestMapping("/admin/image/preView2")
	public byte[] imagePreView2(Map<String,Object> model,
			String path,
			HttpServletRequest request) throws IOException {
		path = new String(path.getBytes("ISO8859-1"), "UTF-8");
		
		String image_path = URLDecoder.decode(path, "UTF-8");System.out.println(image_path);
		
		Resource resource = new FileSystemResource(image_path);
		byte[] fileData = FileCopyUtils.copyToByteArray(resource.getInputStream());
		return fileData;
	}
	
	@RequestMapping("/admin/image/cache")
	public String imageCache(Map<String,Object> model,
			Page page, String id,
			HttpServletRequest request) {
		if ("GET".equals(request.getMethod())) {
			String[] data = null;
			
			String orderby = "name";
			Camera camera = cameraService.getCamera(id);
			
			int p = 1;
			page.setPage(p);
			do {
				List<Image> list = imageService.list(page, orderby, camera);
				
				if(p==1) {
					data = imageService.getCachePath(page.getTotal());
				}
				
				if(data!=null) {
					for (int i=0;i<list.size();i++) {
						if(list.get(i).getCache()==null || list.get(i).getCache().length()<1) {
							int dindex = (page.getPage() - 1) * page.getPageSize() + i;
							
							String dirName = camera.getPath();
							if (dirName.endsWith("\\"))
								dirName = dirName.substring(0, dirName.length()-1);
							dirName = dirName.substring(dirName.lastIndexOf("\\")+1);
							
							String folder = fileConfig.getCachePath() + dirName + "\\" + data[dindex] + "\\";
							File file = new File(folder);
							if(!file.exists()){
								file.mkdir();
							}
							String cache = folder + list.get(i).getName();
							boolean result = imageService.makeCache(list.get(i).getPath(), cache);
							if(result) {
								list.get(i).setCache(cache);
								imageService.save(list.get(i));
							}
							System.out.println("file:"+(dindex+1)+"|path:"+cache);
						}
					}
				}
				
				p = p+1;
				page.setPage(p);
				
			} while (p-1<page.getLast());
			
		}
		
		return "/admin/image/cache";
	}
	
	@RequestMapping("/admin/image/move")
	public String imageMove(Map<String,Object> model,
			Page page, String id,
			HttpServletRequest request) {
		if ("GET".equals(request.getMethod())) {
			
			String orderby = "time";
			Camera camera = cameraService.getCamera(id);
			
			imageService.moveFile(camera, page, orderby);
		}
		return "/admin/image/cache";
	}
	
	@RequestMapping("/admin/image/repeat")
	public String imageRepeat(Map<String,Object> model,
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
	
	@RequestMapping("/admin/image/index")
	public String imageIndex(Map<String,Object> model,
			String orderby, Page page,
			HttpServletRequest request) {
		logger.info(orderby);
		if(orderby==null) {
			if(page==null) {
				page = new Page();
			}
			model.put("page", page);
			
			List<Image> list = imageService.list(page);
			model.put("list", list);
		} else {//if("time".equals(orderby)) 
			List<Image> list = imageService.listOrderBy(orderby);
			model.put("list", list);
		}
		
		return "/admin/image/index";
	}
	
	@RequestMapping("/admin/image/create")
	public String imageCreate(Map<String,Object> model,
			Image image,
			HttpServletRequest request) {
		if("POST".equals(request.getMethod())) {
			logger.info(image.getName() + " path.length:"+image.getPath().length());
			imageService.update(image);
		}
		return "/admin/image/create";
	}
	
	@RequestMapping("/admin/image/edit")
	public String imageEdit(Map<String,Object> model,
			Image image, String id, String export_path, String camera_id,
			HttpServletRequest request) {
		if ("GET".equals(request.getMethod())) {
			Image edit_image = imageService.getImage(id);
			model.put("image", edit_image);
			logger.info(edit_image.toString());
		} else if ("POST".equals(request.getMethod())) {
			logger.info(id);
			Image edit_image = imageService.getImage(id);
			if(edit_image.getCamera()==null || !edit_image.getCamera().getId().equals(camera_id)) {
				Camera camera = cameraService.getCamera(camera_id);
				edit_image.setCamera(camera);
			}
			if(export_path==null || export_path.length()<1) {
//				Export e = new Export();
//				e.setPath(export_path);
//				e.setImage(edit_image);
//				imageService.save(e);
//				edit_image.getExports().add(e);
			} else {
				edit_image.setExports(null);
			}
			edit_image.setName(image.getName());
			edit_image.setPath(image.getPath());
			edit_image.setRate(image.getRate());
			edit_image.setDescription(image.getDescription());
			imageService.save(edit_image);
			return "/image/admin_success";
		}
		return "/admin/image/edit";
	}
	
	@RequestMapping("/admin/image/rescan")
	public String imageReScan(Map<String,Object> model,
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
	
	public static void main(String[] args) {
		String path = "D:\\Sya\\Pictures\\WorkSpace\\Camera\\70D";
		if (path.endsWith("\\"))
			path = path.substring(0, path.length()-1);
		path = path.substring(path.lastIndexOf("\\")+1);
		System.out.println(path);
//		File directory = new File(path);
//		File[] s = directory.listFiles();
//		for(int i=0;i<s.length;i++) {
//			
//			System.out.println(s[i]);
//		}
//		String[] extensions = new String[] {"jpg", "jpeg", "JPG", "png", "gif", "GIF"};
//		Collection<File> listFiles = FileUtils.listFiles(directory, extensions, true);
		System.out.println(123);
	}
	
	@RequestMapping("/admin/image/scan")
	public String imageScan(Map<String,Object> model,
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
	
	@RequestMapping("/admin/image/scan4camera")
	public String imageScanForCamera(Map<String,Object> model,
			String id, 
			HttpServletRequest request) {
		
		Camera c = cameraService.getCamera(id);
		String dir = c.getPath();
		
		Collection<File> files = imageService.scan(dir);
		files = imageService.findFileNotInDB(files);
		for(File file:files) {
			Map<String, String> m = getMetadata(file);
			if (m==null || m.get("time")==null || m.get("time").length()<1
					|| m.get("make")==null || m.get("make").length()<1
					|| m.get("model")==null || m.get("model").length()<1) {
				System.out.println("not photo:"+file.getPath());
				continue;
			}
			Image image = new Image();
			image.setName(file.getName());
			image.setTime(getTime(m.get("time")));
			image.setPath(file.getPath());
			image.setBucket_id(1);
			Camera camera = cameraService.getCamera(m.get("make"), m.get("model"));
			if(camera!=null) {
				image.setCamera(camera);
			}
			imageService.update(image);
		}
		return "/image/admin_success";
	}
	
	@RequestMapping("/admin/image/savescan")
	public String imageSaveScan(String next, String nextPath, String bucket_id,
			String[] index, String[] name, String[] time, String[] maker, String[] model, String[] description, String[] path,
			HttpServletRequest request) throws UnsupportedEncodingException {
		int i = 0;
		Image image;
		String _a , _b , _c, _d = null;
		for(String j:index) {
			_a = name[i];
			_b = path[i];
			_c = time==null||time.length<1?null:time[i];
			_d = description==null||description.length<1?null:description[i];
			image = imageService.getImage(_a, _b);
			if(image==null) {
				image = new Image();
				image.setName(_a);
				image.setTime(getTime(_c));
				image.setPath(_b);
				image.setDescription(_d);
				image.setBucket_id(Integer.valueOf(bucket_id));
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
	
	@RequestMapping("/admin/camera/index")
	public String cameraIndex(Map<String,Object> model,
			String orderby,
			HttpServletRequest request) {
		List<Camera> list = cameraService.cameralist();
		model.put("list", list);
		List<Map<String, String>> data = imageService.groupbyCamera();
		model.put("data", data);
		List<Map<String, String>> data2 = imageService.groupbyCamera2();
		model.put("data2", data2);
		
		int i = 0;
		for(Map<String, String> d:data) {
			i = i + Integer.valueOf(d.get("count"));
		}
		model.put("total", i);
		return "/admin/camera/index";
	}
	
	@RequestMapping("/admin/camera/create")
	public String cameraCreate(Map<String,Object> model,
			Camera camera,
			HttpServletRequest request) {
		if("POST".equals(request.getMethod())) {
			logger.info(camera.getMaker() );
			cameraService.update(camera);
		}
		return "/admin/camera/create";
	}
	
	@RequestMapping("/admin/camera/edit")
	public String cameraEdit(Map<String,Object> model,
			Camera camera, String id,
			HttpServletRequest request) {
		if ("GET".equals(request.getMethod())) {
			Camera edit_camera = cameraService.getCamera(id);
			model.put("camera", edit_camera);
			logger.info(edit_camera.toString());
		} else if ("POST".equals(request.getMethod())) {
			logger.info("description:"+camera.toString());
			cameraService.save(camera);
			return "/image/admin_success";
		}
		return "/admin/camera/edit";
	}
	
	@RequestMapping("/admin/album/index")
	public String albumIndex(Map<String,Object> model,
			Page page,
			HttpServletRequest request) {
		if(page==null) {
			page = new Page();
		}
		List<Album> list = albumService.list(page);
		model.put("list", list);
		model.put("page", page);
		return "/admin/album/index";
	}
	
	@RequestMapping("/admin/album/create")
	public String albumCreate(Map<String,Object> model,
			Album album,
			HttpServletRequest request) {
		if("POST".equals(request.getMethod())) {
			albumService.update(album);
		}
		return "/admin/album/create";
	}
	
	@RequestMapping("/admin/album/edit")
	public String albumEdit(Map<String,Object> model,
			Album album, String id, String[] images_id,
			HttpServletRequest request) {
		if ("GET".equals(request.getMethod())) {
			Album edit_album = albumService.getAlbum(id);
			model.put("album", edit_album);
			logger.info("++++"+edit_album.getImages().size());
		} else if ("POST".equals(request.getMethod())) {
			logger.info("description:"+images_id.length);
			List<Image> images = imageService.list(images_id);logger.info(images.get(0).getName());
			album.setImages(images);
			albumService.save(album);
			return "/image/admin_success";
		}
		return "/admin/album/edit";
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
