package org.hb0712.discovery.service.impl;

import java.util.List;

import org.hb0712.discovery.dao.CameraDao;
import org.hb0712.discovery.mapper.CameraMapper;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.service.CameraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CameraServiceImpl implements CameraService {
	
	@Autowired
	public CameraMapper cameraMapper;
	@Autowired
	private FileConfig fileConfig;
	
	public List<Camera> list(){
		return cameraMapper.list();
	}
	
	public Camera getCamera(String id) {
		return getCamera(Integer.valueOf(id));
	}
	
	public Camera getCamera(Integer id) {
		return cameraMapper.getCamera(Integer.valueOf(id));
	}
	
	public Camera getCamera(String maker, String model) {
		return cameraMapper.getCameraByMakerModel(maker, model);
	}
	
	public boolean update(Camera camera) {
		return cameraMapper.update(camera);
	}
	
	public boolean save(Camera camera) {
		return cameraMapper.save(camera);
	}
	
	public String getPath(Camera camera) {
		return fileConfig.getWorkSpace() + camera.getPath();
	}
}
