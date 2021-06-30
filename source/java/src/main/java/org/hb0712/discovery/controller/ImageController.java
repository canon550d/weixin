package org.hb0712.discovery.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.service.ImageService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ImageController {
	private Logger logger = Logger.getLogger(ImageController.class);
	
	@Autowired
	private ImageService imageService;
	
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
			String date,
			HttpServletRequest request) {
		List<Image> list = imageService.list(date);
		model.put("list", list);
		
		return "image/list";
	}
	
	@RequestMapping("/image/data/timeflow")
	public String data(Map<String,Object> model, HttpServletRequest request) {
		List<Date> list = imageService.listTime();
		model.put("list", list);
		return "image/data";
	}
}
