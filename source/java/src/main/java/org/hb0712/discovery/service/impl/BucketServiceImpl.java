package org.hb0712.discovery.service.impl;

import java.util.List;

import org.hb0712.discovery.mapper.BucketMapper;
import org.hb0712.discovery.pojo.Bucket;
import org.hb0712.discovery.service.BucketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BucketServiceImpl implements BucketService{
	@Autowired
	public BucketMapper bucketMapper;
	
	public List<Bucket> list(){
		return bucketMapper.list();
	}
}
