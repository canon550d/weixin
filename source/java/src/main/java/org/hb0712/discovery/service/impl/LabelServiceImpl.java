package org.hb0712.discovery.service.impl;

import java.util.List;

import org.hb0712.discovery.mapper.LabelMapper;
import org.hb0712.discovery.pojo.Label;
import org.hb0712.discovery.service.LabelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelServiceImpl implements LabelService{
	@Autowired
	public LabelMapper labelMapper;
	
	public List<Label> list() {
		return labelMapper.list();
	}
	
	public boolean addLabelImage(String lid, String iid) {
		labelMapper.addLabelImage(lid, iid);
		return true;
	}

}
