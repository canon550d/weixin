package org.hb0712.discovery.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hb0712.discovery.dao.ImageDao;
import org.hb0712.discovery.pojo.Image;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.jdbc.Work;
import org.hibernate.query.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class ImageDaoImpl implements ImageDao{
	private Logger logger = LogManager.getLogger(ImageDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	private HibernateTemplate ht = null;
	
	private HibernateTemplate getHibernateTemplate(){
		if (ht == null) {
			ht = new HibernateTemplate(sessionFactory);
		}
		return ht;
	}
	
	public List<Image> list(){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Image");
		List<Image> list = query.list();
		logger.info("my name:" + list.get(0).getName());
		session.close();
		return list;
	}
	
	public List<Image> listOrderBy(String orderby){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Image i order by time desc");
		List<Image> list = query.list();
		session.close();
		return list;
	}
	
	public List<Image> list(Date satrt, Date end){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Image i where i.time between :start and :end");
		query.setParameter("start", satrt);//TemporalType.DATE
		query.setParameter("end", end);
		List<Image> list = query.list();
		logger.info("my name:" + list.get(0).getName());
		session.close();
		return list;
	}
	
	public List<Date> listTime(){
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("select distinct i.time from Image i");
		List<Date> list = query.list();
		session.close();
		return list;
	}
	
	public Image getImage(int id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Image i where i.id="+id);
		List<Image> list = query.list();
		session.close();
		return list.get(0);
	}
	
	/*
	 * 查询某目录下某文件
	 */
	public Image getImage(String name, String path) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Image i where i.name = :name and i.path= :path");
		query.setParameter("path", path);
		query.setParameter("name", name);
		List<Image> list = query.list();
		System.out.println("ImageDaoImpl 89:" + name + "|" + path + "|" + (list.size()));
		session.close();
		if(list==null || list.size()<1)
			return null;
		
		return list.get(0);
	}
	
	public boolean update(Image image){
		logger.info("my name:" + image);
		Session session = sessionFactory.openSession();
		session.saveOrUpdate(image);
		session.close();
		return true;
	}
	
	public boolean save(Image image){
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		logger.info("my name:" + image);
		session.update(image);
		ts.commit();
		session.close();
		return true;
	}
}
