package org.hb0712.discovery.service.impl;

import java.util.List;

import org.hb0712.discovery.dao.AlbumDao;
import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Album;
import org.hb0712.discovery.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl implements AlbumService{
	@Autowired
	public AlbumDao albumDao;
	
	public List<Album> list() {
		return albumDao.list();
	}
	
	public List<Album> list(Page page) {
		return albumDao.list(page);
	}
	
	public boolean update(Album album) {
		return albumDao.update(album);
	}
	
	public boolean save(Album album) {
		return albumDao.save(album);
	}
	
	public Album getAlbum(String id) {
		return albumDao.getAlbum(id);
	}
}
