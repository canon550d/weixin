package org.hb0712.discovery.service;

import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Blog;

import java.util.List;

public interface BlogService {
    public List<Blog> list(Page page);
}
