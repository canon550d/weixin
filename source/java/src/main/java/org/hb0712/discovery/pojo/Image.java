package org.hb0712.discovery.pojo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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
	private String cache;
	private Integer rate;//评分
	private Integer bucket_id;
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
	public String getCache() {
		return cache;
	}
	public void setCache(String cache) {
		this.cache = cache;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public Integer getBucket_id() {
		return bucket_id;
	}
	public void setBucket_id(Integer bucket_id) {
		this.bucket_id = bucket_id;
	}
	@ManyToOne(fetch = FetchType.LAZY)
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
	
	@OneToMany(mappedBy = "image", fetch=FetchType.LAZY)
	public List<Export> getExports() {
		return exports;
	}
	public void setExports(List<Export> exports) {
		this.exports = exports;
	}
	@Transient
	public boolean getGxports() {
		if(this.exports==null)
			return false;
		if(this.exports.size()>0) {
			return true;
		}
		return false;
	}
	@Transient
	public boolean getExportsIsEmpty() {
		return !getExportsIsNotEmpty();
	}
	@Transient
	public boolean getExportsIsNotEmpty() {
		if(this.exports==null)
			return false;
		if(this.exports.size()>0) {
			boolean notEmptyPath = false;
			for(Export e:exports) {
				if(e.getPath()!=null && e.getPath().length()>0) {
					notEmptyPath = true;
					return notEmptyPath;
				}
			}
		}
		return false;
	}
	@Transient
	public Export getFirstExport() {
		return this.getExports().get(0);
	}
	
	@Transient
	public String getURLEncoderPath () throws UnsupportedEncodingException {
		return URLEncoder.encode(getPath(), "UTF-8");
	}
	
	public String toString() {
		return this.getId() + "|" + this.getName() + "|" + this.getPath() + "|" + this.getDescription() + "|" + this.getTime();
	}
}
