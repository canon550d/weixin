package org.hb0712.discovery.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hb0712.discovery.dao.BlogDao;
import org.hb0712.discovery.pojo.Blog;

import org.hb0712.discovery.pojo.Label;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BlogDaoImpl extends DefaultDaoImpl<Blog> implements BlogDao {
    private Logger logger = LogManager.getLogger(BlogDaoImpl.class);

    public List<Blog> list(Page page){
        Session session = sessionFactory.openSession();
        List<Blog> list = super.list(session, page, "id desc");
        session.close();
        return list;
    }
}
