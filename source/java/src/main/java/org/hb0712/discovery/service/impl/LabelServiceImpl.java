package org.hb0712.discovery.service.impl;

import java.util.List;

import org.hb0712.discovery.dao.LabelDao;
import org.hb0712.discovery.pojo.Label;
import org.hb0712.discovery.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelServiceImpl implements LabelService{
	@Autowired
	public LabelDao labelDao;
	
	public List<Label> list() {
		return labelDao.list();
	}

	public Label getLabel(String id) {
		return labelDao.getLabel(id);
	}
}
