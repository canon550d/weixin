package hb0712.discovery.dao;

import hb0712.discovery.pojo.Image;

import java.util.List;

public interface ImageDao {
	public List<Image> getImages(String gid);
	public Image edit(Image img);
	public boolean delete(String gid, String id);
	public Image getImage(String gid, String id);
}
