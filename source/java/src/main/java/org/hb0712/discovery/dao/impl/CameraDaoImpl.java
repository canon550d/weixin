package org.hb0712.discovery.dao.impl;

import java.util.List;

import org.hb0712.discovery.dao.CameraDao;
import org.hb0712.discovery.pojo.Camera;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CameraDaoImpl extends DefaultDaoImpl<Camera> implements CameraDao{
	public List<Camera> cameralist() {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Camera order by type,maker");
		List<Camera> list = query.list();
		session.close();
		return list;
	}
	
	public Camera getCamera(String id) {
		return super.get(id);
	}
	
	public Camera getCamera(String maker, String model) {
		Session session = getSessionFactory().openSession();
		Query query = session.createQuery("from Camera i where i.maker = :maker and i.model= :model");
		query.setParameter("maker", maker);
		query.setParameter("model", model);
		List<Camera> list = query.list();
		session.close();
		if(list==null || list.size()<1)
			return null;
		
		return list.get(0);
	}
	
	public boolean update(Camera camera) {
		super.update(camera);
		return true;
	}
	
	public boolean save(Camera camera) {
		super.save(camera);
		return true;
	}
}
