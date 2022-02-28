package org.hb0712.discovery.service;

import java.util.List;

import org.hb0712.discovery.pojo.Label;

public interface LabelService {
	public List<Label> list();
	public Label getLabel(String id);
}
