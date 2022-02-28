package org.hb0712.discovery.dao.impl;

import java.util.List;

import org.hb0712.discovery.dao.LabelDao;
import org.hb0712.discovery.pojo.Label;
import org.springframework.stereotype.Repository;

@Repository
public class LabelDaoImpl extends DefaultDaoImpl<Label> implements LabelDao{

	public List<Label> list() {
		return super.list();
	}

}
