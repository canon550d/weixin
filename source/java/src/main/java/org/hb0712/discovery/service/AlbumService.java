package org.hb0712.discovery.service;

import java.util.List;

import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Album;

public interface AlbumService {
	public List<Album> list();
	
	public List<Album> list(Page page);
	
	public boolean update(Album album);
	
	public boolean save(Album album);
	
	public Album getAlbum(String id);
}
