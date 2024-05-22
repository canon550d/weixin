package org.hb0712.discovery.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hb0712.discovery.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LabelController {
	private Logger logger = LogManager.getLogger(LabelController.class);
	
	@Autowired
	private LabelService labelService;
	
	@RequestMapping("/admin/label/create")
	public String labelCreate(Map<String,Object> model,
			String label_id, String image_id,
			HttpServletRequest request) {
		labelService.addLabelImage(label_id, image_id);
		return "/image/admin_success";
	}
}
