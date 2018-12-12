package com.mcgj.dao;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.SystemConfig;

/**
 * 系统配置dao映射接口
 * @author 杨晨
 * @create_date 2018-12-05 12:33
 * @address 合肥
 *
 */
public interface SystemConfigDAO extends BaseService<SystemConfig, Integer>{
	
	/**
	 * 根据key获取配置的值
	 * @param key
	 * @return
	 */
	SystemConfig selectSystemConfigByKey(String key);
}
