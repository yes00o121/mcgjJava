package com.mcgj.dao;

import java.util.List;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.FileRepertory;

/**
 * �ļ��ֿ�dao
 * @author �
 * @date 2019-03-01
 */
public interface FileRepertoryMapper extends BaseService<FileRepertory, Integer>{
	
	/**
	 * ��ѯ���е��ļ��ֿ�����
	 * @return
	 */
	public List<FileRepertory> selectAll();
}
