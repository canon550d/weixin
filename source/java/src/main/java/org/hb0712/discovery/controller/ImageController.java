package org.hb0712.discovery.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hb0712.discovery.pojo.Album;
import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.pojo.Label;
import org.hb0712.discovery.service.AlbumService;
import org.hb0712.discovery.service.ImageService;
import org.hb0712.discovery.service.LabelService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ImageController {
	private Logger logger = Logger.getLogger(ImageController.class);
	
	@Autowired
	private ImageService imageService;
	@Autowired
	private AlbumService albumService;
	@Autowired
	private LabelService labelService;
	
//	@InitBinder
//	public void initBinder(WebDataBinder binder) {
//		
//	}
	
	@RequestMapping("/image/index")
	public String index(Map<String,Object> model,
			
			HttpServletRequest request) {
		
		return "image/index";
	}
	
	@RequestMapping("/image/list")
	public String list(Map<String,Object> model,
			String date, String album, String label,
			HttpServletRequest request) throws UnsupportedEncodingException {
		
		List<Image> list = null;
		if(StringUtils.isNotEmpty(date)) {
			list = imageService.list(date);
		}else if(StringUtils.isNotEmpty(album)) {
			Album obj_album = albumService.getAlbum(album);
			if (obj_album!=null) {
				list = obj_album.getImages();
			}
		}
		if(StringUtils.isNotEmpty(label)) {
			Label obj_label = labelService.getLabel(label);
			if (obj_label!=null) {
				list = obj_label.getImages();
			}
		}
		for(Image l:list) {
			String p = URLEncoder.encode(l.getPath(), "UTF-8");
			l.setPath(p);
			if (l.getCache()!=null && l.getCache().length()>0) {
				String c = URLEncoder.encode(l.getCache(), "UTF-8");
				l.setCache(c);
			}
		}
		
		model.put("list", list);
		
		return "image/list";
	}
	

}
