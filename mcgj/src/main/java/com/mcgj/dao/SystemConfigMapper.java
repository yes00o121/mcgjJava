package com.mcgj.dao;

import java.util.List;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.SystemConfig;

/**
 * ϵͳ����daoӳ��ӿ�
 * @author �
 * @create_date 2018-12-05 12:33
 * @address �Ϸ�
 *
 */
public interface SystemConfigMapper extends BaseService<SystemConfig, Integer>{
	
	/**
	 * ��ѯ��������
	 * @return
	 */
	List<SystemConfig> selectAll();
}
