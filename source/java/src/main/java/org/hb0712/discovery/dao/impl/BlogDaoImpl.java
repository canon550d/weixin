package org.hb0712.discovery.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hb0712.discovery.dao.BlogDao;
import org.hb0712.discovery.pojo.Blog;
import org.springframework.stereotype.Repository;

@Repository
public class BlogDaoImpl extends DefaultDaoImpl<Blog> implements BlogDao {
    private Logger logger = LoggerFactory.getLogger(BlogDaoImpl.class);

    public List<Blog> list(Page page){
        return null;
    }
}
