package com.mcgj.entity;
/**
 * �ֵ�ʵ����
 * @author ad
 *
 */
public class Dict extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private String dictName;//�ֵ�����
	
	private String codeValue;//����ֵ
	
	private Integer dictType;//�ֵ�����

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
	}

	public Integer getDictType() {
		return dictType;
	}

	public void setDictType(Integer dictType) {
		this.dictType = dictType;
	}
	
}
