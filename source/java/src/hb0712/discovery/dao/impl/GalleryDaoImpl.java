package hb0712.discovery.dao.impl;

import hb0712.discovery.dao.GalleryDao;
import hb0712.discovery.pojo.Gallery;

import java.util.List;

import com.sina.sae.storage.SaeStorage;
import com.sina.sae.storage.StorageUtil;

public class GalleryDaoImpl implements GalleryDao{

	public List<Gallery> getGallery() {
		String accessKey = "4kj4nmnn5l";
		String secretKey = "31zwi3yi5w53550x3hj3iwh1xx5il2hi3zjl0jzj";
		String appName = "xstory";
		String domain = "travel";
		String fileName = "20170329/IMG_2215.jpg";
		SaeStorage storage = new SaeStorage(accessKey, secretKey, appName);
		
		String url = storage.getUrl(domain, fileName);
		System.out.println(url);
		System.out.println("http://xstory-travel.stor.sinaapp.com/20170329/IMG_2215.jpg");
		List<String> list = storage.getList(domain);
		System.out.println(list.size());
		

		return null;
	}

	public static void main(String[] args) {
		GalleryDaoImpl dao = new GalleryDaoImpl();
		dao.getGallery();
	}
}
