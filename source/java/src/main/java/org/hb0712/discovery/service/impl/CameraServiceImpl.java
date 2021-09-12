package org.hb0712.discovery.service.impl;

import java.util.List;

import org.hb0712.discovery.dao.CameraDao;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CameraServiceImpl implements CameraService {
	
	@Autowired
	public CameraDao cameraDao;
	
	public List<Camera> cameralist(){
		return cameraDao.cameralist();
	}
	public Camera getCamera(String id) {
		return cameraDao.getCamera(id);
	}
	public Camera getCamera(String maker, String model) {
		return cameraDao.getCamera(maker, model);
	}
	public boolean update(Camera camera) {
		return cameraDao.update(camera);
	}
	public boolean save(Camera camera) {
		return cameraDao.save(camera);
	}
}
