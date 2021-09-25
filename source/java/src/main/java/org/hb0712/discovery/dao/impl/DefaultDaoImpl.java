package org.hb0712.discovery.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hb0712.discovery.pojo.Image;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultDaoImpl<T> {
	private Logger logger = LogManager.getLogger(DefaultDaoImpl.class);
	
	@Autowired
	protected SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public List<T> list(){
		String name = getSimpleName();
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from "+name);
		List<T> list = query.list();

		session.close();
		return list;
	}

	public List<T> list(Page page){
		String name = getSimpleName();
		Session session = sessionFactory.openSession();
		Query querycount = session.createQuery("select count(*) from "+name);
		Long total = (Long) querycount.uniqueResult();
		page.setTotal(total.intValue());
		
		Query query = session.createQuery("from "+name);
		query.setFirstResult(page.getStartPosition());
		query.setMaxResults(page.getPageSize());
		List<T> list = query.list();
		
		session.close();
		return list;
	}
	
	public List<T> list2(Session session, Page page){
		String name = getSimpleName();
		Query querycount = session.createQuery("select count(*) from "+name);
		Long total = (Long) querycount.uniqueResult();
		page.setTotal(total.intValue());
		
		Query query = session.createQuery("from "+name);
		query.setFirstResult(page.getStartPosition());
		query.setMaxResults(page.getPageSize());
		List<T> list = query.list();
		
		return list;
	}
	
	public List<T> SQLQuery(Session session, String sql, Map<String, Object> map, Class entityType) {
		Query query = session.createSQLQuery(sql).addEntity(entityType);
		for(String key:map.keySet()) {
			query.setParameter(key, map.get(key));
		}
		List<T> list = query.list();
		return list;
	}
	
	public boolean update(T t) {
		logger.info("my name:" + t);
		Session session = sessionFactory.openSession();
		session.saveOrUpdate(t);
		session.close();
		return true;
	}
	
	public boolean save(T t) {
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		logger.info("my name:" + t);
		session.update(t);
		ts.commit();
		session.close();
		return true;
	}
	
	public T get(int id) {
		String str_id = String.valueOf(id);
		return get(str_id);
	}
	
	public T get(String id) {
		String name = getSimpleName();
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from "+name+" i where i.id="+id);
		List<T> list = query.list();
		session.close();
		return list.get(0);
	}
	
	private String getSimpleName() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> c = (Class<T>) pt.getActualTypeArguments()[0];
		return c.getSimpleName();
	}
}
