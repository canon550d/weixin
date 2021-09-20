package org.hb0712.discovery.dao.impl;

import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hb0712.discovery.dao.ImageDao;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.pojo.Export;
import org.hb0712.discovery.pojo.Image;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public class ImageDaoImpl extends DefaultDaoImpl<Image> implements ImageDao{
	private Logger logger = LogManager.getLogger(ImageDaoImpl.class);
	
	public List<Image> list(Page page){
		return super.list(page);
	}
	
	public List<Image> list(Page page, String orderby, Camera camera){
		Session session = sessionFactory.openSession();
		Query querycount = session.createQuery("select count(*) from Image i where i.camera = ?1");
		querycount.setParameter(1, camera);
		Long total = (Long) querycount.uniqueResult();
		page.setTotal(total.intValue());
		
		Query query = session.createQuery("from Image i where i.camera = ?1 order by ?2");
		query.setParameter(1, camera);
		query.setParameter(2, orderby);
		query.setFirstResult(page.getStartPosition());
		query.setMaxResults(page.getPageSize());
		List<Image> list = query.list();

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
		return super.get(id);
	}
	
	public List<Image> list(Integer[] ids) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Image i where i.id in (:ids)");
		query.setParameterList("ids", ids);
		List<Image> list = query.list();
		session.close();
		return list;
	}
	
	public Image getImageByName(String name) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Image i where i.name = :name ");
		query.setParameter("name", name);
		List<Image> list = query.list();
		session.close();
		if(list==null || list.size()<1)
			return null;
		
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
		super.update(image);
		return true;
	}
	
	public boolean save(Image image){
		super.save(image);
		return true;
	}
	public boolean save(Export export) {
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		logger.info("my name:" + export);
		session.save(export);
		ts.commit();
		session.close();
		return true;
	}
}
