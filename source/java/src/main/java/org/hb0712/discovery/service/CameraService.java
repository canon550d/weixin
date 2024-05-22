package org.hb0712.discovery.service;

import java.util.List;

import org.hb0712.discovery.pojo.Camera;

public interface CameraService {
	
	public List<Camera> list();
	
	public Camera getCamera(Integer id);
	
	public Camera getCamera(String id);
	
	public Camera getCamera(String maker, String model);
	
	public boolean update(Camera camera);
	
	public boolean save(Camera camera);
	
	public String getPath(Camera camera);
}
