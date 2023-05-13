package org.hb0712.discovery.service.impl;

import org.hb0712.discovery.dao.BlogDao;
import org.hb0712.discovery.dao.ImageDao;
import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Blog;
import org.hb0712.discovery.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    public BlogDao blogDao;

    public List<Blog> list(Page page) {
        return blogDao.list(page);
    }
}
