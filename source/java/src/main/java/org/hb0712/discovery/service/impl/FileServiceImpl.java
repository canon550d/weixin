package org.hb0712.discovery.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.coobird.thumbnailator.Thumbnails;

public class FileServiceImpl {
	private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	
	public String[] getCachePath(int listsize){
		String[] data = new String[listsize];
		
		int j = 100;//每个文件夹100个
		int k = 100;//文件夹起始编号
		for(int i=0;i<listsize;i++) {
			data[i] = String.valueOf(k);
			
			if ((i+1)%j==0) {
				k = k+1;
			}
		}
		return data;
	}
	
	public boolean makeCache(String source, String target) {
		logger.info(source + " to " + target);
		if(FileUtils.getFile(target).exists()) {
			return true;
		}
		File file = new File(source);
		try {
			BufferedImage image = ImageIO.read(file);
			
			int width = Sample.Small.getCalculatedWidth(image);
			int height = Sample.Small.getFixedHeight();
			
			Thumbnails.of(source).size(width, height).outputQuality(0.4).toFile(target);
			image = null;
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public enum Sample {
		Small(160, 0.4, "缩略图"),
		Middle(2592, 0.8,"浏览大图");
		
		private Integer longSide;
		private Double quality;
		private String name;
		
		Sample(Integer longSide, Double quality, String name){
			this.longSide = longSide;
			this.quality = quality;
			this.name = name;
		}
		
		public Integer getWidth(BufferedImage image) {
			if (image.getWidth() > image.getHeight()) {//横拍
				return longSide;
			} else {//竖拍
				return image.getWidth() * this.longSide / image.getHeight();
			}
		}
		
		public Integer getHeight(BufferedImage image) {
			if (image.getWidth() > image.getHeight()) {//横拍
				return image.getHeight() * this.longSide / image.getWidth();
			} else {//竖拍
				return longSide;
			}
		}
		
		public Integer getCalculatedWidth(BufferedImage image) {
			if (image.getWidth() > image.getHeight()) {//横拍
				return image.getWidth() * this.longSide / image.getHeight();
			} else {//竖拍
				return image.getHeight() * this.longSide / image.getWidth();
			}
		}
		
		public Integer getFixedHeight() {
			return longSide;
		}

		public Double getQuality() {
			return quality;
		}

		public String getName() {
			return name;
		}
	}
	
	
}
