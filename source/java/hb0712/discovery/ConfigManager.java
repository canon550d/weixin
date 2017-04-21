package hb0712.discovery;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;



public class ConfigManager {
	private String configName = "hb0712\\discovery\\config.properties";
	private static ConfigManager instance;
	
	private ConfigManager(){
		
	}
	
	public static ConfigManager getInstance() {
		if (instance == null) {
			instance = new ConfigManager();
		}
		return instance;
	}
	
	/*
	 * 保存文件的路径
	 */
	public String getSavePath(){
		return this.readValue("savePath");
	}
	
	public String getVMPath(){
		return this.readValue("vmPath");
	}
	
	public String getHttpPath(){
		return this.readValue("httpPath");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String path = ConfigManager.getInstance().getSavePath();
		System.out.println(path);
	}

	 //根据key读取value
	private String readValue(String key) {
		Properties props = new Properties();
		InputStream input = null;
		try {
			input = new ClassPathResource(configName).getInputStream();
			props.load(input);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally{
			if(input!=null){
				try {
					input.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
