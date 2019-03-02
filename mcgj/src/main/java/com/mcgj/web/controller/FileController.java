package com.mcgj.web.controller;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.dao.FileRepertoryMapper;
import com.mcgj.entity.FileRepertory;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.utils.HttpClientUtil;
import com.mcgj.utils.MessageUtil;
import com.mcgj.utils.PropertiesUtil;

/**
 * �ļ�������
 * @author �
 * @date 2019-02-28
 * @descript �ṩһЩ�ļ���صķ���
 *
 */
@Controller
@RequestMapping("/file")
public class FileController extends AbstractBaseController{
	
	private Logger log = Logger.getLogger(FileController.class);
	
	@Autowired
	private FileRepertoryMapper fileRepertoryMapper;
	
	/**
	 * �ϴ�������վ���ļ�
	 * @param url ��ַ
	 * @return
	 */
	@RequestMapping("/upLoadRemoteFile")
	@ResponseBody
	public synchronized String  upLoadRemoteFile(@RequestParam("url") String url){
		
		Object mongoId = RedisHashUtil.get(PropertiesUtil.get("redisConifg.properties","fileRepertory" ), url);
		//�жϻ������Ƿ��и�ͼƬ,û�в������ݿ�ͻ���
		if(mongoId != null){
			return mongoId.toString();
		}
		InputStream fileInputStream = null;
		try {
			//��ȡ������ļ���������
			fileInputStream = HttpClientUtil.getFileInputStream(url);
			String mondoid = mongoDBRemoteFileService.upload(fileInputStream);
			FileRepertory record = new FileRepertory(HttpClientUtil.getHost(url),url,mondoid);
			//�����ݲ���
			fileRepertoryMapper.insert(record);
			RedisHashUtil.put(PropertiesUtil.get("redisConifg.properties", "fileRepertory"), url, mondoid);
			return mondoid;
		} catch (Exception e) {
			log.error(MessageUtil.MSG_UPLOAD_FILE_ERROR);
		}finally {
			if(fileInputStream != null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					log.error(MessageUtil.MSG_CLOSE_STREAM_ERROR);
				}
			}
		}
		return null;
		
	}
//	@RequestMapping("/select")
//	public void select(){
//		
//		Map<String,Object> map = new HashMap<>();
//		RedisHashUtil.put("mcgj", "https://github.com/yes00o121/mcgjJava/blob/master/mcgj/src/main/java/com/mcgj/redis/RedisHashUtil.java", "46fdsfsd46fsdfsd465fsd");
//		System.out.println(RedisHashUtil.get("mcgj", "https://github.com/es00o121/mcgjJava/blob/master/mcgj/src/main/java/com/mcgj/redis/RedisHashUtil.java"));
//	}
}
