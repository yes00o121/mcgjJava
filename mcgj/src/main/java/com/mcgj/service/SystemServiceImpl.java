package com.mcgj.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgj.dao.SystemConfigMapper;
import com.mcgj.entity.SystemConfig;
import com.mcgj.utils.MessageUtil;

/**
 * ϵͳ����ҵ���
 * @author �
 * @create_date 2018-120-05 12:31
 * @address �Ϸ�
 *
 */
@Service
public class SystemServiceImpl implements ISystemConfigService{
	
	@Autowired
	private SystemConfigMapper systemConfigDAO;

	@Override
	public void delete(Integer id) {
		
	}

	@Override
	public void insert(SystemConfig record) {
		
	}

	@Override
	public void update(SystemConfig record) {
		
	}

	@Override
	public SystemConfig selectById(Integer id) {
		return null;
	}

	@Override
	public String selectSystemConfigByKey(String key) {
		return null;
//		SystemConfig systemConfig = systemConfigDAO.selectSystemConfigByKey(key);
//		if(systemConfig == null){
//			throw new RuntimeException(MessageUtil.MSG_GET_SYSTEMCONFIG_ERROR);
//		}
//		return systemConfig.getValue();
	}

}
