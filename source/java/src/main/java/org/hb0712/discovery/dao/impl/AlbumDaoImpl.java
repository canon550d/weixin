package org.hb0712.discovery.dao.impl;

import java.util.List;

import org.hb0712.discovery.dao.AlbumDao;
import org.hb0712.discovery.pojo.Album;
import org.hb0712.discovery.pojo.Image;
import org.hibernate.Hibernate;
import org.hibernate.Session;
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
		Session session = sessionFactory.openSession();
		Album album = super.get(session, id);
		List<Image> images = album.getImages();
		for (Image i:images) {
			Hibernate.initialize(i.getCamera());
		}
		session.close();
		return album;
	}
}
