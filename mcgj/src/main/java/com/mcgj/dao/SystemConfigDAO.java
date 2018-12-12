package com.mcgj.dao;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.SystemConfig;

/**
 * ϵͳ����daoӳ��ӿ�
 * @author �
 * @create_date 2018-12-05 12:33
 * @address �Ϸ�
 *
 */
public interface SystemConfigDAO extends BaseService<SystemConfig, Integer>{
	
	/**
	 * ����key��ȡ���õ�ֵ
	 * @param key
	 * @return
	 */
	SystemConfig selectSystemConfigByKey(String key);
}
