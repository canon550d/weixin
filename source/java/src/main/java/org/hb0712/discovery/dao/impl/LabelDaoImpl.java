package org.hb0712.discovery.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.hb0712.discovery.dao.LabelDao;
import org.hb0712.discovery.pojo.Album;
import org.hb0712.discovery.pojo.Image;
import org.hb0712.discovery.pojo.Label;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class LabelDaoImpl extends DefaultDaoImpl<Label> implements LabelDao{

	public List<Label> list() {
		return super.list();
	}
	
	public Label getLabel(String id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Label l left join fetch l.images i where l.id="+id+" order by l.id asc,i.time desc");
		List<Label> list = query.list();
		Label label = list.get(0);
		List<Image> images = label.getImages();
		for (Image i:images) {
			Hibernate.initialize(i.getCamera());
		}
		session.close();
		return label;
	}
	
	public boolean addLabelImage(String label_id, String image_id) {
		Session session = sessionFactory.openSession();
		Query querycount = session.createSQLQuery("select count(*) from label_image i where label_id = ?1 and image_id = ?2");
		querycount.setParameter(1, label_id);
		querycount.setParameter(2, image_id);
		BigInteger total = (BigInteger) querycount.uniqueResult();
		if (total.intValue()==0) {
			Transaction ts = session.beginTransaction();
			Query queryInsert = session.createSQLQuery("insert into label_image (`label_id`,`image_id`) value ('"+label_id+"','"+image_id+"')");
			int i = queryInsert.executeUpdate();
			try {
				ts.commit();
			} catch (Exception e) {
				ts.rollback();
				e.printStackTrace();
			}finally{
				session.close();
			}
			return true;
		}
		session.close();
		return false;
	}
	
	public Label searchLabel(String name) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Label l left join fetch l.images i where l.name='"+name+"' order by l.id asc,i.time desc");
		List<Label> list = query.list();
		Label label = list.get(0);
		List<Image> images = label.getImages();
		for (Image i:images) {
			Hibernate.initialize(i.getCamera());
		}
		session.close();
		return label;
	}
}
