package org.hb0712.discovery.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.service.CameraService;
import org.hb0712.discovery.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping ("/admin/camera")
public class CameraController {
	private Logger logger = LogManager.getLogger(CameraController.class);
	
	@Autowired
	private CameraService cameraService;
	@Autowired
	private ImageService imageService;
	
	@ResponseBody
	@RequestMapping("/list")
	public String list() throws UnsupportedEncodingException {
		List<Camera> cameras = cameraService.list();
		StringBuffer result = new StringBuffer();
		result.append("{").append("\"code\":\"200\",");
		result.append("\"data\":").append("{");
		
		result.append("\"list\":").append("[");
		for (int i=0;i<cameras.size();i++) {
			result.append("{");
			result.append("\"id\":").append(cameras.get(i).getId()).append(",");
			result.append("\"maker\":\"").append(cameras.get(i).getMaker()).append("\",");
			result.append("\"model\":\"").append(cameras.get(i).getModel()).append("\",");
			result.append("\"description\":\"").append(cameras.get(i).getDescription()).append("\",");
			result.append("\"path\":\"").append(URLEncoder.encode(cameras.get(i).getPath(), "UTF-8")).append("\",");
			result.append("\"type\":\"").append(cameras.get(i).getType()).append("\"");
			result.append("}");
			if (i<cameras.size()-1)
				result.append(",");
		}
		result.append("]");
		
		result.append("}").append("}");
		return result.toString();
	}
	
	// checked
	@RequestMapping("/index")
	public String cameraIndex(Map<String,Object> model,
			String orderby,
			HttpServletRequest request) {
		List<Camera> list = cameraService.list();
		model.put("list", list);
		
		List<Map<String, Long>> data = imageService.groupbyCamera();
		model.put("data", data);
		List<Map<String, Long>> data2 = imageService.groupbyCamera2();
		model.put("data2", data2);
		
		int i = 0;
		for(Map<String, Long> d:data) {
			i = i + d.get("count").intValue();
		}
		model.put("total", i);
//		fileConfig.getCachePath();
//		model.put("total", i);
		
//		Map<String, Map<String, Integer>> k = new TreeMap<String, Map<String, Integer>>();
//		for(Camera c:list) {
//			String d = cameraComponent.getPath(c);
//			File df = new File(d);
//			System.out.println(df.listFiles().length);
//			
//			Map<String, Integer> map = new TreeMap<String, Integer>();
//			for (File dcf :df.listFiles()) {
//				map.put(dcf.getName(), dcf.listFiles().length);
//				System.out.println(dcf.listFiles().length);
//			}
//			k.put(c.getPath(), map);
//		}
//		model.put("fileCount", k);
		
		return "/admin/camera/index";
	}
	
	@RequestMapping("/create")
	public String cameraCreate(Map<String,Object> model,
			Camera camera,
			HttpServletRequest request) {
		if("POST".equals(request.getMethod())) {
			logger.info(camera.getMaker() );
			cameraService.update(camera);
		}
		return "/admin/camera/create";
	}
	
	@RequestMapping("/edit")
	public String cameraEdit(Map<String,Object> model,
			Camera camera, String id,
			HttpServletRequest request) {
		if ("GET".equals(request.getMethod())) {
			Camera edit_camera = cameraService.getCamera(id);
			model.put("camera", edit_camera);
			//logger.info(edit_camera.toString());
		} else if ("POST".equals(request.getMethod())) {
			//logger.info("id:"+id);
			Camera edit_camera = cameraService.getCamera(camera.getId());
			edit_camera.setDescription(camera.getDescription());
			edit_camera.setPath(camera.getPath());
			edit_camera.setPath_length(camera.getPath().length());
			edit_camera.setSize(camera.getSize());
			cameraService.update(edit_camera);
			return "/image/admin_success";
		}
		return "/admin/camera/edit";
	}
}
