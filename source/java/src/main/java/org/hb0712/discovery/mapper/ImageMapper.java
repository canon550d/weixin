package org.hb0712.discovery.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.pojo.Image;

@Mapper
public interface ImageMapper {
	/**
	 * 统计当前相机的相片数量
	 * @param camera
	 * @return
	 */
	public Integer getCount(@Param("camera") Camera camera);
	
	public List<Image> getImages(@Param("page") Page page, @Param("camera") Camera camera, @Param("orderby") String orderby);
	
	public List<Image> getImagesByCameraId(String camera_id);
	
	public List<Image> getImagesByCameraId7time(String camera_id);
	
	public List<Image> queryImagesByFullpaths(@Param("md5s") List<Map<String, String>> md5s);
	
	public List<Image> getImagesByIds(Integer[] ids);
	
	public List<Image> getImagesByTimes(@Param("start") String start, @Param("end") String end);
	
	public Image getImageById(Integer id);
	
	public Image getImageByName(@Param("name") String name);
	
	public Image getImageByNamePath(String name, String path);
	
	public Image getImageByMD5(String md5);
	
	public List<Map<String, Long>> groupbyCamera();
	
	public List<Map<String, Long>> groupbyCamera2();
	
	public List<Date> listTime();
	
	public boolean save(Image image);
	
	public boolean update(Image image);
	
	public boolean updatePath (@Param("path") String path, @Param("id") Integer id);
	
	public boolean updateCache(Image image);
	
	public int updateState(String ids);
	
	public boolean updateNumname(@Param("numname") String path, @Param("numname") Integer id);
}
