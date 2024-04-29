package org.hb0712.discovery.dao.impl;

import java.util.List;

import org.hb0712.discovery.dao.LabelDao;
import org.hb0712.discovery.pojo.Label;
import org.springframework.stereotype.Repository;

@Repository
public class LabelDaoImpl extends DefaultDaoImpl<Label> implements LabelDao{

	public List<Label> list() {
		return null;
	}
	
	public Label getLabel(String id) {
		return null;
	}
	
	public boolean addLabelImage(String label_id, String image_id) {
		return false;
	}
	
	public Label searchLabel(String name) {
		return null;
	}
}
