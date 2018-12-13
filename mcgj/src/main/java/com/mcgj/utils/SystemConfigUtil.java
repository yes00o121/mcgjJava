package com.mcgj.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mcgj.dao.SystemConfigMapper;
import com.mcgj.entity.SystemConfig;

/**
 * ϵͳ���ù�����,��ȡϵͳ���õ�����
 * @author �
 * @date 2018-12-12 14:47
 * @address ����
 */
public class SystemConfigUtil {
	
	private SystemConfigUtil(){}
	
	//�����������
	private static Map<String,String> values = new HashMap<String,String>();
	
	static{
		reload();
	}
	
	/**
	 * ����Key��ȡvalue
	 * @return value
	 */
	public static String getSystemConfigByKey(String key){
		return values.get(key);
	}
	
	//ˢ����������
	public  static void reload(){
		SystemConfigMapper systemConfigDAO = (SystemConfigMapper)SpringUtil.getBean(SystemConfigMapper.class);
		List<SystemConfig> selectAll = systemConfigDAO.selectAll();
		for(SystemConfig config:selectAll){
			values.put(config.getKey(), config.getValue());
		}
	}
	
}
