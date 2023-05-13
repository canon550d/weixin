package org.hb0712.discovery.pojo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

/*
 * 副本，其实就是一个File
 */
@Entity
@Table(name = "export")
public class ImageFile {
	private Integer id;
	private String path;
	private Image image;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	@ManyToOne
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	@Transient
	public String getURLEncoderPath () throws UnsupportedEncodingException {
		return URLEncoder.encode(getPath(), "UTF-8");
	}
}
