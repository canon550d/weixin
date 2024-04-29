package org.hb0712.discovery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.hb0712.discovery.dao.impl.Page;
import org.hb0712.discovery.pojo.Album;

@Mapper
public interface AlbumMapper {

	public List<Album> list(Page page);
	
	public boolean update(Album album);
	
	public boolean save(Album album);
	
	public Album getAlbum(String id);
}
