package org.hb0712.discovery.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hb0712.discovery.dao.ImageDao;
import org.hb0712.discovery.pojo.Camera;
import org.hb0712.discovery.pojo.Export;
import org.hb0712.discovery.pojo.Image;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDaoImpl extends DefaultDaoImpl<Image> implements ImageDao{
	private Logger logger = LogManager.getLogger(ImageDaoImpl.class);
	
	public List<Image> list(Page page){
		Session session = sessionFactory.openSession();
		List<Image> list = super.list2(session, page);
		for (Image i:list) {
			Hibernate.initialize(i.getCamera());
			for (Export e:i.getExports()) {
				Hibernate.initialize(e);
			}
		}
		session.close();
		return list;
	}
	
	public List<Image> list(Page page, String orderby, Camera camera){
		Session session = sessionFactory.openSession();
		Query querycount = session.createQuery("select count(*) from Image i where i.camera = ?1 and i.state = 0");
		querycount.setParameter(1, camera);
		Long total = (Long) querycount.uniqueResult();
		page.setTotal(total.intValue());
		
		Query query = session.createQuery("from Image i where i.camera = ?1 and i.state = 0 order by orderby".replace("orderby", orderby));
		query.setParameter(1, camera);
		
		query.setFirstResult(page.getStartPosition());
		query.setMaxResults(page.getPageSize());
		List<Image> list = query.list();

		for (Image i:list) {
			Hibernate.initialize(i.getCamera());
			for (Export e:i.getExports()) {
				Hibernate.initialize(e);
			}
		}

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
	
	public List<Image> listRepeat(String camera_id){
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery("select * from image i where camera_id = ?1").addEntity(Image.class);
		query.setParameter(1, camera_id);
		List<Image> list = query.list();
		System.out.println(list.size() +"|" + camera_id);
		session.close();
		
		List<Image> list2 = new ArrayList<Image>();
		for (Image i:list) {
			for (Image i2:list) {
				i.getName();
				i.getTime();
				if (!list2.contains(i)  && !list2.contains(i2)
						&& i.getName().equals(i2.getName()) && i.getTime().compareTo(i2.getTime())==0
						&& !i.getPath().equals(i2.getPath())) {
					list2.add(i);
					list2.add(i2);
					break;
				}
			}
		}
		
		return list2;
	}
	
	public boolean listRepeatRemove(String[] id){
		if (id == null || id.length<1) {
			return false;
		}
		String ids = "";
		for (int i=0;i<id.length;i++) {
			if(i==0) {
				ids = id[i];
			} else {
				ids += ","+id[i];
			}
		}
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		Query query = session.createSQLQuery("update image set state = 1 where id in ("+ids+")");
//		query.setParameterList("ids", id);
		int i = query.executeUpdate();
		try {
			ts.commit();
		} catch (Exception e) {
			ts.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		if(i>0) {
			return true;
		}
		return false;
	}
	
	public List<Image> list(Date satrt, Date end){
		Session session = sessionFactory.openSession();
		/*
		Query query = session.createSQLQuery("select {i.*}, {c.*}, {e.*} from image i join camera c on c.id = i.camera_id left join export e on e.image_id = i.id where i.time between :start and :end order by time")
				.addEntity("i",Image.class)
				.addEntity("c",Camera.class)
				.addEntity("e",Export.class);
		query.setParameter("start", satrt);
		query.setParameter("end", end);
		
		Set<Image> set = new HashSet<Image>();
		List list = query.getResultList();
		Iterator<Object> iterator = list.iterator();
		while(iterator.hasNext()) {
			Object[] o=(Object[]) iterator.next();
			Image img = (Image) o[0];
			if(set.contains(img)) {
				// do nothing
			} else {
				set.add(img);
			}
		}
		session.close();
		
		List<Image> result = new ArrayList<Image>(set);
		return result;
		*/
		
		/*
		 * 因为Image里的fetch使用了FetchType.LAZY，所以为了避免N+1的问题，需要在hql的join后面增加fetch来查询
		 * select distinct i 如果没有的话，会产生多条结果
		 */
		Query query = session.createQuery("select distinct i from Image i join fetch i.camera c left join fetch i.exports e where i.time between :start and :end and i.state = 0 order by time");
		query.setParameter("start", satrt);
		query.setParameter("end", end);
		List<Image> list = query.getResultList();
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
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		
		String sql = "select * from image i join camera c on c.id = i.camera_id left join export e on e.image_id = i.id where i.id = :id";
		List<Image> list = super.SQLQuery(session, sql, map, Image.class);
		for (Image i:list) {
			Hibernate.initialize(i.getCamera());
			for (Export e:i.getExports()) {
				Hibernate.initialize(e);
			}
		}
		session.close();
		return list.get(0);
//		return null;
	}
	
	public List<Image> list(Integer[] ids) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Image i where i.id in (:ids)");
		query.setParameterList("ids", ids);
		List<Image> list = query.list();
		session.close();
		return list;
	}
	
	public List<Image> list(String[] paths) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Image i where i.path in (:paths)");
		query.setParameterList("paths", paths);
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
	
	public boolean updatePath (String path, Integer id){
		Session session = sessionFactory.openSession();
		Transaction ts = session.beginTransaction();
		Query query = session.createSQLQuery("update image set path = ?1 where id = ?2");
		query.setParameter(1, path);
		query.setParameter(2, id);
		int i = query.executeUpdate();
		try {
			ts.commit();
		} catch (Exception e) {
			ts.rollback();
			e.printStackTrace();
		}finally{
			session.close();
		}
		if(i>0) {
			return true;
		}
		return false;
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
	
	public List<Map<String, String>> groupbyCamera() {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery("select camera_id, count(*) from image where state = 0 group by camera_id");
		List<?> list = query.list();
		for(Object m:list) {
			Object[] line = (Object[]) m;
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", line[0].toString());
			map.put("count", line[1].toString());
			data.add(map);
		}
		session.close();
		return data;
	}
	
	public List<Map<String, String>> groupbyCamera2() {
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		Session session = sessionFactory.openSession();
		Query query = session.createSQLQuery("select camera_id, count(*) from image where state = 0 and cache is NULL group by camera_id");
		List<?> list = query.list();
		for(Object m:list) {
			Object[] line = (Object[]) m;
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", line[0].toString());
			map.put("count", line[1].toString());
			data.add(map);
		}
		session.close();
		return data;
	}
}
