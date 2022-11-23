package org.hb0712.discovery.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.coobird.thumbnailator.Thumbnails;

public class FileServiceImpl {
	private Logger logger = LogManager.getLogger(FileServiceImpl.class);
	
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
			int cacheSize = 160;
			int width = 0;
			int height = 0;
			if (image.getWidth() > image.getHeight()) {//横拍
				width = image.getWidth() * cacheSize / image.getHeight();
				height = cacheSize;
			} else {//竖拍
				width = image.getHeight() * cacheSize / image.getWidth();
				height = cacheSize;
			}
			Thumbnails.of(source).size(width, height).toFile(target);
			image = null;
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void test2() {
		String t = "D:\\Sya\\Pictures\\WorkSpace\\Camera\\LX5\\";
		String s = "D:\\Sya\\Pictures\\Cache\\";
		
		int listsize = 1000;
		int j = 100;//每个文件夹100个
		int k = 100;//文件夹起始编号
		for(int i=0;i<listsize;i++) {
			System.out.println("file:"+(i+1)+"|path:"+s+k+"\\sss.jpg");
			if ((i+1)%j==0) {
				k = k+1;
			}
		}
	}
	
	public void test1() throws IOException {
		// 通常来说，我们需要对图片做缓存
		System.out.println(11);
		//先实验图片压缩后的效果
		String jpg ="D:\\Sya\\Pictures\\WorkSpace\\Camera\\LX5\\100\\P1030357.JPG";
		String out = "D:\\Sya\\Pictures\\Cache\\LX5\\101\\P1030357.JPG";
		
		File file = new File(jpg);
		BufferedImage image = ImageIO.read(file);
		int cacheSize = 160;
		int width = 0;
		int height = 0;
		if (image.getWidth() > image.getHeight()) {//横拍
			width = image.getWidth() * cacheSize / image.getHeight();
			height = cacheSize;
		} else {//竖拍
			width = image.getHeight() * cacheSize / image.getWidth();
			height = cacheSize;
		}
		Thumbnails.of(file).size(width, height).toFile(out);
		//压缩的还可以，但是拍摄信息都没有了
	}
	
	public static void main(String[] args) throws IOException{
		new FileServiceImpl().test1();
	}
}
