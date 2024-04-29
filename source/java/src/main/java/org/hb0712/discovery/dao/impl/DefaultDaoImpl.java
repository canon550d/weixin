package org.hb0712.discovery.dao.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultDaoImpl<T> {
	private Logger logger = LoggerFactory.getLogger(DefaultDaoImpl.class);

	public List<T> list(){
		return null;
	}

	public List<T> list(Page page){
		return null;
	}
	
	public List<T> list(Page page, String orderby){
		return null;
	}
	
	public List<T> list2(Page page){
		return null;
	}
	
	public List<T> SQLQuery(String sql, Map<String, Object> map, Class entityType) {
		return null;
	}
	
	public boolean update(T t) {
		return true;
	}
	
	public boolean save(T t) {
		return true;
	}
	
	public T get(int id) {
		return null;
	}
	
	public T get(String id) {
		return null;
	}
	
	private String getSimpleName() {
		return null;
	}
}
