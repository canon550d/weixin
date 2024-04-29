package org.hb0712.discovery.dao.impl;

import java.util.List;

import org.hb0712.discovery.dao.AlbumDao;
import org.hb0712.discovery.pojo.Album;

public class AlbumDaoImpl extends DefaultDaoImpl<Album> implements AlbumDao{

	public List<Album> list() {
		return null;
	}
	
	public List<Album> list(Page page){
		return null;
	}
	
	public boolean update(Album album) {
		return true;
	}
	
	public boolean save(Album album) {
		return true;
	}
	
	public Album getAlbum(String id) {
		return null;
	}
}
