package com.mcgj.service;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.SystemConfig;

/**
 * ϵͳ���ýӿ�
 * @author �
 * @create_date 2018-12-05 12:30
 * @address �Ϸ�
 *
 */
public interface ISystemConfigService extends BaseService<SystemConfig, Integer>{

	/**
	 * ����key��ȡ���õ�ֵ
	 * @param key
	 * @return
	 */
	String selectSystemConfigByKey(String key);
}
