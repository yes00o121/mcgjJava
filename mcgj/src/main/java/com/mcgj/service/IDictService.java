package com.mcgj.service;

import java.util.List;

import com.mcgj.base.service.BaseService;
import com.mcgj.entity.Dict;

public interface IDictService extends BaseService<Dict,Integer>{
	
	/**
	 * ���ݴ���Ĵ���ֵ��ѯ
	 * @return
	 */
	public List<Dict> selectDictByCode(String codeValue);
}
