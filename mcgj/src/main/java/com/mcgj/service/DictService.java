package com.mcgj.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcgj.dao.DictMapper;
import com.mcgj.entity.Dict;

@Service
public class DictService implements IDictService{
	
	@Autowired
	private DictMapper dictMapper;

	public void delete(Integer id) {
		
	}

	public void insert(Dict record) {
		
	}

	public void update(Dict record) {
		
	}

	public Dict selectById(Integer id) {
		return null;
	}
	
	/**
	 * ���ݴ���Ĵ���ֵ��ѯ
	 * @return
	 */
	public List<Dict> selectDictByCode(String codeValue) {
		if(codeValue == null || "".equals(codeValue)){
			throw new RuntimeException("����ֵ����Ϊ��");
		}
		List<Dict> dicts = dictMapper.selectDictByCode(codeValue);
		return dicts;
	}

}
