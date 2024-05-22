package org.hb0712.discovery.service.impl;

import java.util.List;

import org.hb0712.discovery.dao.AlbumDao;
import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.mapper.AlbumMapper;
import org.hb0712.discovery.pojo.Album;
import org.hb0712.discovery.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AlbumServiceImpl implements AlbumService{
	@Autowired
	public AlbumMapper albumMapper;
	
	public List<Album> list(Page page) {
		return albumMapper.list(page);
	}
	
	public boolean update(Album album) {
		return albumMapper.update(album);
	}
	
	public boolean save(Album album) {
		return albumMapper.save(album);
	}
	
	public Album getAlbum(String id) {
		return albumMapper.getAlbum(id);
	}
}
