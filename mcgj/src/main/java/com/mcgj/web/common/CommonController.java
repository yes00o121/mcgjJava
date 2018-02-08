package com.mcgj.web.common;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.mcgj.redis.RedisHashUtil;
import com.mcgj.utils.PropertiesUtil;
import com.mcgj.utils.RandomGraphic;
import com.mcgj.web.controller.AbstractBaseController;

@RequestMapping("/common")
@Controller
public class CommonController extends AbstractBaseController{
	
	private static final Logger log = LoggerFactory.getLogger(CommonController.class);
	
	
	/**
	 * ����ͼƬ����
	 * @param imgId
	 * @param response
	 */
	@RequestMapping("/image")
	@Async
	public void image(String imgId,HttpServletResponse response) {
		response.setContentType("image");
		try {
			ServletOutputStream out = response.getOutputStream();
			mongoDBRemoteFileService.download(imgId, out);
		} catch (Exception e) {
			log.error(Marker.ANY_MARKER, e);
		}
	}
	/**
	 * �����ļ�
	 * @param mongoid
	 * @param fileName
	 * @param model
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/downloadFile")
	public void downloadFile(String mongoid,String fileName,Model model,HttpServletResponse response) throws IOException {
		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename="+(fileName!=null?fileName:mongoid));
		mongoDBRemoteFileService.download(mongoid, response.getOutputStream());
	}
	/**
	 * �ϴ��ļ�����
	 * @return
	 */
	@RequestMapping("/uploadFile")
	@ResponseBody
	public String uploadFile(MultipartFile file){
		String imageUrl = "";
		try{
			imageUrl = super.uploadFile(file);
		}catch(Exception e){
			e.printStackTrace();
		}
		return imageUrl;
	}
	
	/**
	 * ������֤��
	 */
	@RequestMapping("/generateVerification")
	@Async
	public void generateVerification(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		try{
			//��ȡǰ�˱��ش�ŵ�verificationCode
			String verificationCode =request.getParameter("verificationCode");
			if(verificationCode == null || "".equals(verificationCode)){
				throw new RuntimeException("��ȡ��֤��ʧ��");
			}
//			Properties p = new Properties();
//			FileInputStream file = new FileInputStream("./delay.properties");
//			InputStream  is = CommonController.class.getClassLoader().getResourceAsStream("delay.properties");
//			p.load(is);
//			String time = p.getProperty("verification_delay");//��ȡ��ʱʱ��
			Integer time = PropertiesUtil.getVerificationDelay();
			RandomGraphic rg = new RandomGraphic();
			String strs = rg.getRandomCharacter();//��ȡ������ɵ���֤�ַ�
			BufferedImage bi = rg.createGraphic(strs);//����ͼƬ
			RedisHashUtil.setex(verificationCode,strs,time);//����ǰ��������ɵ�Ψһ��ʶ��Ϊkey���룬���������֤
			//����ͼƬ���û�
			ImageIO.write(bi,"jpg",response.getOutputStream());
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
		
	}
}
