package com.mcgj.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import com.mcgj.service.IRemoteFileMongoSer;
import com.mcgj.service.MongoDBRemoteFileService;
/**
 * ���п��������࣬ʵ����һЩMongo�Ĳ���
 * @author ad
 *
 */
public class AbstractBaseController {
	
	@Autowired
	protected IRemoteFileMongoSer mongoDBRemoteFileService;
	
	/**
	 * �ϴ��ļ�
	 * @param file
	 * @param entity
	 * @param propertyName
	 * @throws Exception
	 */
	public String uploadFile(MultipartFile file) throws Exception{
		return "/common/image?imgId="+mongoDBRemoteFileService.upload(file.getInputStream());
	}
	
}
