package org.hb0712.discovery.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "image")
public class Image {
	private Integer id;
	private String name;//文件名
	private Date time;//拍摄时间
	private String description;
	private String path;//路径
	private Integer rate;//评分
	private Camera camera;//相机
	private Album album;
	private List<Export> exports;
	
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
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
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
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	@ManyToOne
	public Camera getCamera() {
		return camera;
	}
	public void setCamera(Camera camera) {
		this.camera = camera;
	}
//	@ManyToOne
//	@JoinColumn(name = "album_id")
//	public Album getAlbum() {
//		return album;
//	}
//	public void setAlbum(Album album) {
//		this.album = album;
//	}
	
	@OneToMany(mappedBy = "image", fetch=FetchType.EAGER)
	public List<Export> getExports() {
		return exports;
	}
	public void setExports(List<Export> exports) {
		this.exports = exports;
	}
	@Transient
	public boolean hasExports() {
		if(this.exports==null)
			return false;
		if(this.exports.size()>0) {
			return true;
		}
		return false;
	}
	public String toString() {
		return this.getId() + "|" + this.getName() + "|" + this.getPath() + "|" + this.getDescription() + "|" + this.getTime();
	}
}
