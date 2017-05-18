package hb0712.discovery.controller;

import hb0712.discovery.pojo.Gallery;
import hb0712.discovery.pojo.Image;
import hb0712.discovery.service.GalleryService;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
	
	@RequestMapping("/gallery/add")
	public String add(String name, String intro,
			Map<String,Object> model,
			HttpServletRequest request, HttpSession httpSession){
		if(StringUtils.isNotEmpty(name)){
			Gallery g = new Gallery();
			g.setName(name);
			g.setIntro(intro);
			galleryService.save(g);
			return "redirect:index.aspx";
		}
		return "gallery/add";
	}
	
	@RequestMapping("/gallery/edit")
	public String edit(String id, String name, String intro,
			Map<String,Object> model,
			HttpServletRequest request, HttpSession httpSession){
		if(StringUtils.isNotEmpty(name)){
			Gallery g = new Gallery();
			g.setId(id);
			g.setName(name);
			g.setIntro(intro);
			galleryService.edit(g);
			return "redirect:index.aspx";
		}
		Gallery gallery = galleryService.getGallery(id);
		model.put("gallery", gallery);
		return "gallery/edit";
	}
	
	@RequestMapping("/gallery/addImage")
	public String addImage(String gid, String path, String type, String intro,
			Map<String,Object> model,
			HttpServletRequest request, HttpSession httpSession){
		Gallery g = null;
		if(StringUtils.isNotEmpty(gid)){
			g = galleryService.getGallery(gid);
		}
		if(StringUtils.isNotEmpty(path)){
			Image i = new Image();
			i.setPath(path);
			i.setType(type);
			i.setIntro(intro);
			i.setGid(gid);
			i.setGallery(g);
			
			galleryService.save(i);
			return "redirect:index.aspx";
		}
		List<Gallery> list = galleryService.getGallery();
		model.put("galleries", list);
		return "gallery/addImage";
	}
	
	@RequestMapping("/gallery/image/edit")
	public String editImage(String gid, String id, String path, String type, String intro,
			Map<String,Object> model,
			HttpServletRequest request, HttpSession httpSession){
		Gallery g = null;
		if(StringUtils.isNotEmpty(gid)){
			g = galleryService.getGallery(gid);
		}
		if(StringUtils.isNotEmpty(path)){
			Image img = new Image();
			img.setId(id);
			img.setPath(path);
			img.setType(type);
			img.setIntro(intro);
			img.setGid(gid);
			img.setGallery(g);
			galleryService.edit(img);
			return "redirect:../index.aspx";
		}
		Image image = galleryService.getImage(gid, id);
		model.put("image", image);
		List<Gallery> list = galleryService.getGallery();
		model.put("galleries", list);
		return "gallery/editImage";
	}
	
	@RequestMapping("/gallery/image/delete")
	public String deleteImage(String gid, String id,
			Map<String,Object> model,
			HttpServletRequest request, HttpSession httpSession){
		galleryService.delete(gid, id);
		return "redirect:../index.aspx";
	}
}
