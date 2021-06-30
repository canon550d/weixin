package org.hb0712.discovery.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "image")
public class Image {
	private Integer id;
	private String name;
	private Date time;
	private String description;
	private String path;
	
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
	@DateTimeFormat(pattern="yyyy-MM-dd")
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
//	@Column(name = "time", length = 10)
//	@Temporal(TemporalType.DATE)
//	public Date getDate() {
//		return time;
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
	
	public String toString() {
		return this.getId() + "|" + this.getName() + "|" + this.getPath() + "|" + this.getDescription() + "|" + this.getTime();
	}
}
