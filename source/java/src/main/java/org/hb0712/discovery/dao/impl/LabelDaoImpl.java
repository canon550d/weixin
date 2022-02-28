package org.hb0712.discovery.dao.impl;

import java.util.List;

import org.hb0712.discovery.dao.LabelDao;
import org.hb0712.discovery.pojo.Album;
import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.pojo.Label;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class LabelDaoImpl extends DefaultDaoImpl<Label> implements LabelDao{

	public List<Label> list() {
		return super.list();
	}
	
	public Label getLabel(String id) {
		Session session = sessionFactory.openSession();
		Label label = super.get(session, id);
		List<Image> images = label.getImages();
		for (Image i:images) {
			Hibernate.initialize(i.getCamera());
		}
		session.close();
		return label;
	}
}
