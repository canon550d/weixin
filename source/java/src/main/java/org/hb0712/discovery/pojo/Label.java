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

@Entity
@Table(name = "label")
public class Label {
	private Integer id;
	private String name;
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
	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(name="label_image", 
		joinColumns = {@JoinColumn(name = "label_id")},
		inverseJoinColumns = {@JoinColumn(name = "image_id")})
	public List<Image> getImages() {
		return images;
	}
	public void setImages(List<Image> images) {
		this.images = images;
	}
}
