package org.hb0712.discovery.pojo;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class Camera {
	private Integer id;
	private String maker;//品牌
	private String model;//型号
	private String description;
	private String path;
	private Integer path_length;//相片的路径长度
	private String size;//目录大小
	private String type;//DC,mobile,SLR
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
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
	public Integer getPath_length() {
		return path_length;
	}
	public void setPath_length(Integer path_length) {
		this.path_length = path_length;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getURLEncoderPath () throws UnsupportedEncodingException {
		return URLEncoder.encode(getPath(), "UTF-8");
	}
	
	public String toString() {
		return "id:"+this.id;
	}
}
