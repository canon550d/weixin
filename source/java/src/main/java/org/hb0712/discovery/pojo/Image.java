package org.hb0712.discovery.pojo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;


public class Image {
	private Integer id;
	private String name;//文件名
	private Date time;//拍摄时间
	private String description;
	private String path;//路径
	private String cache;
	private String md5;
	private Integer rate;//评分
	private Integer state;
	private Bucket bucket;
	private Camera camera;//相机
	private Album album;
	private String numname;
	private List<ImageFile> files;
	private List<Label> labels;
	
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
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public Integer getRate() {
		return rate;
	}
	public void setRate(Integer rate) {
		this.rate = rate;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getNumname() {
		return numname;
	}
	public void setNumname(String numname) {
		this.numname = numname;
	}
	public Bucket getBucket() {
		return bucket;
	}
	public void setBucket(Bucket bucket) {
		this.bucket = bucket;
	}
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
	public List<ImageFile> getFiles() {
		return files;
	}
	public void setFiles(List<ImageFile> files) {
		this.files = files;
	}
	public List<Label> getLabels() {
		return labels;
	}
	public void setLabels(List<Label> labels) {
		this.labels = labels;
	}

	public boolean getGxports() {
		if(this.files==null)
			return false;
		if(this.files.size()>0) {
			return true;
		}
		return false;
	}

	public boolean getFilesIsEmpty() {
		return !getFilesIsNotEmpty();
	}

	public boolean getFilesIsNotEmpty() {
		if(this.files==null)
			return false;
		if(this.files.size()>0) {
			boolean notEmptyPath = false;
			for(ImageFile e:files) {
				if(e.getPath()!=null && e.getPath().length()>0) {
					notEmptyPath = true;
					return notEmptyPath;
				}
			}
		}
		return false;
	}

	public ImageFile getFirstFile() {
		return this.getFiles().get(0);
	}
	

	public String getURLEncoderPath () throws UnsupportedEncodingException {
		return URLEncoder.encode(getPath(), "UTF-8");
	}
	
	public String toString() {
		return this.getId() + "|" + this.getName() + "|" + this.getPath() + "|" + this.getDescription() + "|" + this.getTime();
	}
}
