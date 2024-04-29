package org.hb0712.discovery.dao.impl;

import java.util.List;

import org.hb0712.discovery.dao.CameraDao;
import org.hb0712.discovery.pojo.Camera;
import org.springframework.stereotype.Repository;

@Repository
public class CameraDaoImpl extends DefaultDaoImpl<Camera> implements CameraDao{
	public List<Camera> cameralist() {
		return null;
	}
	
	public Camera getCamera(String id) {
		return null;
	}
	
	public Camera getCamera(String maker, String model) {
		return null;
	}
	
	public boolean update(Camera camera) {
		return true;
	}
	
	public boolean save(Camera camera) {
		return true;
	}
}
