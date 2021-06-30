package org.hb0712.discovery.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;


public class DaoImplTest {
	private Logger logger = LogManager.getLogger(DaoImplTest.class);
	protected Session session;
	protected Transaction transaction;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}
	
	@Before
	public void setUp() throws Exception {
		logger.debug("创建数据库链接");
//		AnnotationConfiguration cfg = new AnnotationConfiguration();
		Configuration cfg = new Configuration();
		SessionFactory sessionFactory = cfg.configure().buildSessionFactory();
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
	}
	
	@After
	public void tearDown() throws Exception {
		logger.debug("释放资源");
		try{
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
		}
		session.close();
	}
}
