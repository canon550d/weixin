package org.hb0712.discovery.dao;

import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Blog;

import java.util.List;

public interface BlogDao {
    public List<Blog> list(Page page);
}
