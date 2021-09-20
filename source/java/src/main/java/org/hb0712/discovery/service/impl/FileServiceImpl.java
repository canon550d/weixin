package org.hb0712.discovery.service.impl;

import java.io.IOException;

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
		try {
			Thumbnails.of(source).scale(0.20f).toFile(target);
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
		String out = "D:\\Sya\\Pictures\\Cache\\LX5\\100\\P1030357.JPG";
		Thumbnails.of(jpg).scale(0.20f).toFile(out);
		//压缩的还可以，但是拍摄信息都没有了
	}
	
	public static void main(String[] args) throws IOException{
		new FileServiceImpl().test1();
	}
}
