package org.hb0712.discovery.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Album;
import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.service.AlbumService;
import org.hb0712.discovery.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AlbumController {
	private Logger logger = LogManager.getLogger(AlbumController.class);
	
	@Autowired
	private AlbumService albumService;
	@Autowired
	private ImageService imageService;
	
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
}
