package hb0712.discovery.pojo;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class Gallery {
	// 对象物理属性
	private String id;
	private String fileName;//图片的物理地址，是Storage里的
	private Date time;
	
	private String intro;//简介，很多时候可以通过一张照片描述一个故事


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	
}
