package org.hb0712.discovery.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.hb0712.discovery.pojo.Album;
import org.hb0712.discovery.pojo.Label;
import org.hb0712.discovery.service.AlbumService;
import org.hb0712.discovery.service.ImageService;
import org.hb0712.discovery.service.LabelService;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CommonController {
	private Logger logger = Logger.getLogger(CommonController.class);
	
	@Autowired
	private ImageService imageService;
	
	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private LabelService labelService;
	
	@RequestMapping("/image/data/timeflow")
	public String data(Map<String,Object> model, HttpServletRequest request) {
		Set<String> list = imageService.listTime();
		model.put("list", list);
		Set<String> year = imageService.listYear(list);
		model.put("year", year);
		
		List<Album> albums = albumService.list();
		model.put("albums", albums);
		
		List<Label> labels = labelService.list();
		model.put("labels", labels);
		
		return "image/data";
	}
}
