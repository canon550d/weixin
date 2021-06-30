package org.hb0712.discovery.controller;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.service.ImageService;
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
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifSubIFDDirectory;

@Controller
public class AdministratorController {
	private Logger logger = Logger.getLogger(AdministratorController.class);
	
	@Autowired
	private ImageService imageService;

	@ResponseBody
	@RequestMapping("/admin/image/preView")
	public byte[] imagePreView(Map<String,Object> model,
			String id,
			HttpServletRequest request) throws IOException {
		Image image = imageService.getImage(Integer.valueOf(id));
		Resource resource = new FileSystemResource(image.getPath());
		byte[] fileData = FileCopyUtils.copyToByteArray(resource.getInputStream());
		return fileData;
	}
	
	@RequestMapping("/admin/image/index")
	public String imageIndex(Map<String,Object> model,
			String orderby,
			HttpServletRequest request) {
		logger.info(orderby);
		if(orderby==null) {
			List<Image> list = imageService.list();
			model.put("list", list);
		} else {//if("time".equals(orderby)) 
			List<Image> list = imageService.listOrderBy(orderby);
			model.put("list", list);
		}
		
		return "/image/admin_index";
	}
	
	@RequestMapping("/admin/image/create")
	public String imageCreate(Map<String,Object> model,
			Image image,
			HttpServletRequest request) {
		if("POST".equals(request.getMethod())) {
			logger.info(image.getName() + " path.length:"+image.getPath().length());
			imageService.update(image);
		}
		return "/image/admin_create";
	}
	
	@RequestMapping("/admin/image/edit")
	public String imageEdit(Map<String,Object> model,
			Image image, String id,
			HttpServletRequest request) {
		if ("GET".equals(request.getMethod())) {
			Image edit_image = imageService.getImage(id);
			model.put("image", edit_image);
			logger.info(edit_image.toString());
		} else if ("POST".equals(request.getMethod())) {
			logger.info("description:"+image.toString());
			imageService.save(image);
			return "/image/admin_success";
		}
		return "/image/admin_edit";
	}
	
	@RequestMapping("/admin/image/scan")
	public String imageScan(Map<String,Object> model,
			String path,
			HttpServletRequest request) {
		
		if(path==null) {
			model.put("image", "E:\\Sya\\图片\\Image\\2015-01-13\\IMG_7788.JPG");
		}else {
			Collection<File> files = imageService.scan(path);
			model.put("files", files);
			
			List<Map<String, String>> list = new ArrayList<Map<String, String>>();
			for (File file : files) {
				//据说Sanselan这个开源库不错，晚点试试

				Map<String, String> metadata = getMetadata(file);
				list.add(metadata);
//				long time = file.lastModified();
//				s.add(new Date(time));
			}
			model.put("list", list);
		}
		return "/image/admin_scan";
	}
	
	@RequestMapping("/admin/image/savescan")
	public String imageSaveScan(
			String[] index, String[] name, String[] time, String[] maker, String[] model, String[] description, String[] path,
			HttpServletRequest request) {
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
				System.out.println(image);
			}
			System.out.println(name[i]);
			i++;
		}
		
		return "/image/admin_scan";
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
					lines.put("time", time);
				}
			}
			return lines;
		}
		return null;
	}
}
