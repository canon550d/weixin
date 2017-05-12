package hb0712.discovery.dao.impl;

import hb0712.discovery.dao.GalleryDao;
import hb0712.discovery.pojo.Gallery;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sina.sae.storage.SaeStorage;
import com.sina.sae.storage.StorageUtil;

@Repository
public class GalleryDaoImpl implements GalleryDao{

	public List<Gallery> getGallery() {
		String accessKey = "";
		String secretKey = "";
		String appName = "";
		String domain = "";
		String fileName = "";
		SaeStorage storage = new SaeStorage(accessKey, secretKey, appName);
		
		String url = storage.getUrl(domain, fileName);
		System.out.println(url);
		System.out.println("");
		List<String> list = storage.getList(domain);
		System.out.println(list.size());
		

		return null;
	}

	public static void main(String[] args) {
		GalleryDaoImpl dao = new GalleryDaoImpl();
		dao.getGallery();
	}
}
