package hb0712.discovery.controller;

import hb0712.discovery.pojo.Gallery;
import hb0712.discovery.service.GalleryService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GalleryController {
	@Autowired
	private GalleryService galleryService;
	
	@RequestMapping("/gallery/index")
	public String index(Map<String,Object> model,
			HttpServletRequest request, HttpSession httpSession){
		
		List<Gallery> list = galleryService.getGallery();
		model.put("gallery", list);
		return "gallery/list";
	}
	
	@RequestMapping("/gallery/view")
	public String view(String id,
			Map<String,Object> model,
			HttpServletRequest request, HttpSession httpSession){
		Gallery gallery = galleryService.getGallery(id);
		model.put("gallery", gallery);
		return "gallery/view";
	}
	
	public String add(){
		return "gallery/add";
	}
	
	public String addImage(){
		return "gallery/addImage";
	}
}
