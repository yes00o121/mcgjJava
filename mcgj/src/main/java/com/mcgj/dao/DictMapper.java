package com.mcgj.dao;

import java.util.List;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.Dict;

public interface DictMapper extends BaseService<Dict,Integer>{
	
	/**
	 * ���ݴ���Ĵ���ֵ��ѯ
	 * @return
	 */
	public List<Dict> selectDictByCode(String codeValue);
}
