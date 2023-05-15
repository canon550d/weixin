package org.hb0712.discovery.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.hb0712.discovery.pojo.Album;
import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.pojo.Label;
import org.hb0712.discovery.service.AlbumService;
import org.hb0712.discovery.service.ImageService;
import org.hb0712.discovery.service.LabelService;
import org.hb0712.discovery.service.impl.FileConfig;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageController {
	private Logger logger = Logger.getLogger(ImageController.class);
	
	@Autowired
	private ImageService imageService;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private LabelService labelService;
	@Autowired
	private FileConfig fileConfig;
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		
//	}
	
	@RequestMapping("/image/index")
	public String index(Map<String,Object> model,
			
			HttpServletRequest request) {
		
		return "image/index";
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println("%E4%BB%8A%E5%A4%A9%E5%90%83%E4%BB%80%E4%B9%88");
		System.out.println(URLDecoder.decode("%E4%BB%8A%E5%A4%A9%E5%90%83%E4%BB%80%E4%B9%88", "UTF-8"));
	}
	
	@RequestMapping("/image/list")
	public String list(Map<String,Object> model,
			String date, String album, String label, String keyword,
			HttpServletRequest request) throws UnsupportedEncodingException {
		
		List<Image> list = null;
		if(StringUtils.isNotEmpty(date)) {logger.info(date);
			list = imageService.list(date);
		} else if (StringUtils.isNotEmpty(album)) {logger.info(album);
			Album obj_album = albumService.getAlbum(album);
			if (obj_album!=null) {
				list = obj_album.getImages();
			}
		} else if (StringUtils.isNotEmpty(label)) {logger.info(label);
			Label obj_label = labelService.getLabel(label);
			if (obj_label!=null) {
				list = obj_label.getImages();
			}
		} else if (StringUtils.isNotEmpty(keyword)) {
			String _keyword = URLDecoder.decode(keyword, "UTF-8");System.out.println(_keyword);
			Label obj_label = labelService.searchLabel(_keyword);
			if (obj_label!=null) {
				list = obj_label.getImages();
			}
		}
		for(Image l:list) {
//			String p = URLEncoder.encode(l.getPath(), "UTF-8");
			String p = l.getPath().replace(fileConfig.getWorkSpace(), "").replace("\\", "/");
			l.setPath(p);
			if (l.getCache()!=null && l.getCache().length()>0) {
//				String c = URLEncoder.encode(l.getCache(), "UTF-8");
				System.out.println(l.getCache());
				String c = l.getCache().replace(fileConfig.getCachePath(), "").replace("\\", "/");
				l.setCache(c);System.out.println(l.getCache());
			}
		}
		
		model.put("list", list);
		
		return "image/list";
	}
	
	/*
	 * /image/view/Camera/550D/111/IMG_1576.JPG
	 * 
	 */
	@ResponseBody
	@RequestMapping({"cache/{ptype:\\S+}/{pmodel:\\S+}/{pindex:\\S+}/{pname:\\S+}.JPG",
		"cache/{ptype:\\S+}/{pmodel:\\S+}/{pindex:\\S+}/{pname:\\S+}.jpg",
		"{ptype:\\S+}/{pmodel:\\S+}/{pindex:\\S+}/{pname:\\S+}.JPG",
		"{ptype:\\S+}/{pmodel:\\S+}/{pindex:\\S+}/{pname:\\S+}.jpg"})
	public byte[] view(Map<String,Object> model,
			String path, String type,
			HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		path = request.getPathInfo().substring(1);
		String newPath = null;
		if (path.startsWith("cache")) {
			path = path.substring(6);
			newPath = fileConfig.getCachePath().replace("\\", "/") + path;
		} else {
			newPath = fileConfig.getWorkSpace().replace("\\", "/") + path;
		}
		
		Resource resource = new FileSystemResource(newPath);
		if (!resource.exists()) {
			resource = new ClassPathResource("404.jpg");
		}
		byte[] fileData = IOUtils.toByteArray(resource.getInputStream());
		return fileData;
	}
}
