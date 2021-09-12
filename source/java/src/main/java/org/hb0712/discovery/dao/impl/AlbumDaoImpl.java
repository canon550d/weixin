package org.hb0712.discovery.dao.impl;

import java.util.List;

import org.hb0712.discovery.dao.AlbumDao;
import org.hb0712.discovery.pojo.Album;
import org.springframework.stereotype.Repository;

@Repository
public class AlbumDaoImpl extends DefaultDaoImpl<Album> implements AlbumDao{

	public List<Album> list() {
		return super.list();
	}
	
	public List<Album> list(Page page){
		return super.list(page);
	}
	
	public boolean update(Album album) {
		super.update(album);
		return true;
	}
	
	public boolean save(Album album) {
		super.save(album);
		return true;
	}
	
	public Album getAlbum(String id) {
		return super.get(id);
	}
}
