package hb0712.discovery.pojo;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

@Entity
public class Gallery {
	// 对象物理属性
	private String id;
	private String name;//相册名称
	private List<Image> images;//图片，是Storage里的
	private Date time;
	
	private String intro;//简介，很多时候可以通过一组照片描述一个故事


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setImages(List<Image> images) {
		this.images = images;
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
