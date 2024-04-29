package org.hb0712.discovery.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.hb0712.discovery.pojo.Bucket;

@Mapper
public interface BucketMapper {
	public List<Bucket> list();
	
	public Bucket getBucket(Integer id);

}
