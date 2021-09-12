package org.hb0712.discovery.dao;

import java.util.List;

import org.hb0712.discovery.pojo.Camera;

public interface CameraDao {
	
	public List<Camera> cameralist();
	
	public Camera getCamera(String id);
	
	public Camera getCamera(String maker, String model);
	
	public boolean update(Camera camera);
	
	public boolean save(Camera camera);
}
