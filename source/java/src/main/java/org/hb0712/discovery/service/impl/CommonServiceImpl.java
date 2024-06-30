package org.hb0712.discovery.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonServiceImpl {
	public static SimpleDateFormat sdf;
	
	public static String getStrTime(Date date) {
		if (sdf ==null) {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		return sdf.format(date);
	}
	
}
