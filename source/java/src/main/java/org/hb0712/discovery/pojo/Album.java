package org.hb0712.discovery.pojo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
 * 影集，相册
 * 把一类图片放到一起，例如一个持续几天的活动
 */
@Entity
@Table(name = "album")
public class Album {
	private Integer id;
	private String name;
//	private String icon;
	private String description;
	private String path;
	private String time;
	private List<Image> images;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
//	public String getIcon() {
//		return icon;
//	}
//	public void setIcon(String icon) {
//		this.icon = icon;
//	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(name="album_image", 
		joinColumns = {@JoinColumn(name = "album_id")},
		inverseJoinColumns = {@JoinColumn(name = "image_id")})
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
	
//	public String toString() {
//		if(images==null)
//			return "";
//		return "images.size:"+images.size();
//	}
}
