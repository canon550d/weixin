package org.hb0712.discovery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hb0712.discovery.pojo.Camera;

@Mapper
public interface CameraMapper {
	public List<Camera> list();
	
	public Camera getCamera(Integer id);
	
	public Camera getCameraByMakerModel(@Param("maker") String maker, @Param("model") String model);
	
	public boolean update(Camera camera);
	
	public boolean save(Camera camera);
	
}
