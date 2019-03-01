package com.mcgj.init;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.mcgj.dao.FileRepertoryMapper;
import com.mcgj.entity.FileRepertory;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.utils.PropertiesUtil;

/**
 * �ļ��ֿ�bean
 * @author �
 * @date 2019-03-01
 * @address ����
 * @descript ��ʼ���ļ��ֿ�bean,�ṩһЩ��ʼ������
 */
public class FileRepertoryBean {
	
	Logger log = Logger.getLogger(FileRepertoryBean.class);
	
	@Autowired
	private FileRepertoryMapper fileRepertoryMapper;
	
	public void init(){
		List<FileRepertory> list = fileRepertoryMapper.selectAll();
		if(list != null && list.size() != 0){
			Map<String,Object> tempMap = new HashMap<>();
			for(FileRepertory file:list){
				tempMap.put(file.getAddress(), file.getMongodbId());
			}
			RedisHashUtil.putAll(PropertiesUtil.get("redisConifg.properties","fileRepertory" ), tempMap);
			System.out.println("��ʼ���ļ��ֿ⵽redis...");
		}
	}
	
	public void close(){
		
	}
}
