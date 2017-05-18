package hb0712.discovery.dao.impl;

import hb0712.discovery.GalleryManage;
import hb0712.discovery.dao.ImageDao;
import hb0712.discovery.pojo.Image;
import hb0712.discovery.utils.SheetBean;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class ImageDaoImpl implements ImageDao {
	@Autowired
	@Qualifier("imageSheetBean")
	private SheetBean sb;
	
	public List<Image> getImages(String gid){
		GalleryManage manage = GalleryManage.instence();
		return manage.getImages(sb, gid);
	}
	
	public Image edit(Image img){
		GalleryManage manage = GalleryManage.instence();
		manage.edit(img, sb);
		return img;
	}
	
	public boolean delete(String gid, String id){
		GalleryManage manage = GalleryManage.instence();
		manage.delete(gid, id, sb);
		return true;
	}
	
	public Image getImage(String gid, String id){
		GalleryManage manage = GalleryManage.instence();
		return manage.getImage(gid, id, sb);
	}
}
