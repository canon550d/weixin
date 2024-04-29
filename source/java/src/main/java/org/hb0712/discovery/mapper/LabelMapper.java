package org.hb0712.discovery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.hb0712.discovery.pojo.Label;

@Mapper
public interface LabelMapper {
	
	public List<Label> list();

	public boolean addLabelImage(String label_id, String image_id);
}
