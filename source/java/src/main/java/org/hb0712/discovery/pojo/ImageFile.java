package org.hb0712.discovery.pojo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/*
 * 副本，其实就是一个File
 */
public class ImageFile {
	private Integer id;
	private String path;
	private Image image;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
	public String getURLEncoderPath () throws UnsupportedEncodingException {
		if (getPath() == null) {
			return null;
		}
		return URLEncoder.encode(getPath(), "UTF-8");
	}
}
