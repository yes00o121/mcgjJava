package com.yc;

import it.sauronsoftware.base64.Base64;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ����ͼƬ
 * @author ad
 *
 */
public class Image {
	
	private String url;//����·��
	
	private String filePath;//����·��
	
	private String hostpath = "";//��Ŀ¼
	
	private static final String IMAGE_REGULAR = "<img.*?(.*?>)";//ͼƬ����
	
	private static final String IMAGE_REGULAR_BASE64 = "data:image.*?('|\")";//ͼƬƥ��base64��ͼƬ
	
	
	public Image(){
		
	}
	public Image(String url,String path){
		this.url = url;
		this.filePath = path;
		this.hostpath = Util.getAbsPath(url);//��ȡ��Ŀ¼
		this.ImageDownLoad();
	}
	/**
	 * ͼƬ���ط���
	 */
	private void ImageDownLoad(){
		String html = Util.getHTML(this.url);//����url,��ȡhtmlҳ��
		this.analyseHtmlStreamToImageElement(html);//������ȡ���е�ͼƬ��ǩ
	}
	
	/**
	 * �õ�ҳ��������img��ǩ
	 */
	private void analyseHtmlStreamToImageElement(String html){
		try{
			if(html == null)
				return;
			List<String> imgs = new ArrayList<String>();
			Pattern pattern = Pattern.compile(IMAGE_REGULAR);
			Pattern patternBase64 = Pattern.compile(IMAGE_REGULAR_BASE64);
			Matcher matcher = pattern.matcher(html);
			Matcher matcherBase64 = patternBase64.matcher(html);//�鿴��ҳ���Ƿ���base64��ͼƬ
			while(matcher.find()){
				imgs.add(matcher.group());
			}
			while(matcherBase64.find()){
				imgs.add(matcherBase64.group().replace("'",""));//׷�Ӳ����������һ����
			}
			getImgElementUrl(imgs);//����img��ǩ
//			return imgs;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
//		return null;
	}
	/**
	 * �������е�img��ǩ���õ�����ͼƬ��ַ
	 * @return
	 */
	private  List<String> getImgElementUrl(List<String> imgs){
		System.out.println(imgs);
		if(imgs == null || imgs.size() == 0)
			return null;
		try{
			for(String img:imgs){
				//�ж��Ƿ�Ϊbase64��ͼƬ�������ֱ������ͼƬ�����ؽ��к�������
				if(img.startsWith("data:image/jpeg;base64,")){
					img = img.replace("data:image/jpeg;base64,","");//��տ�ͷ
					//���Խ�������
					InputStream is =new ByteArrayInputStream(new Base64().decode(img.getBytes()));//��base64ת��Ϊ������
					Util.writeData(is,new FileOutputStream(this.filePath+new Date().getTime()+".jpg"),img);
					continue;
				}
				//�ж�ͼƬ��ǩ�Ƿ����src�����û��ֱ������
				if(!img.contains("src"))
					continue;
				if(img.split("src=\"").length<2)
					continue;//��ͼ��ͼƬ·�������ϱ�׼�����й���
				img = img.split("src=\"")[1];//��ȡurl
				int stratChar = img.indexOf("\"");//�õ���ʼ��"��
				img = img.substring(0,stratChar);
				//�ж�ͼƬ��ַ�Ƿ�Ϊ��֪������
				if(img.startsWith("http")){//http��ͼƬֱ�ӽ�������
					Util.writeData(getImgStream(img),new FileOutputStream(this.filePath+Util.subImageName(img)),img);
					continue;
				}
				if(img.startsWith("//")){// //��ͷ��ǰ�油��http:,һ��Ϊ��̬ͼƬ
					img = "http:"+img;
					Util.writeData(getImgStream(img),new FileOutputStream(this.filePath+Util.subImageName(img)),img);
					continue;
				}
				if(img.startsWith("/")){// /��ͷ���������·����ǰ�����ҳ��ĸ�Ŀ¼
					img = hostpath+img;
					Util.writeData(getImgStream(img),new FileOutputStream(this.filePath+Util.subImageName(img)),img);
				}
			}
			//����ͼƬ�������
			Main.center_text.append("���ؽ���,����⵽"+imgs.size()+"��ͼƬ");
			Main.BtnActive();
		}catch(Exception e){
			e.printStackTrace();
			Main.center_text.append("����ʧ��"+e.getMessage());
			//����ʧ�ܺ�ԭ���غ�ѡ���ļ��а�ť
			Main.BtnActive();
		}
		//��ȡ��ǩ��src�ĵ�ַ
//		.replace("data:image/jpeg;base64,","")
		return null;
	}
	/**
	 * ��ȡͼƬ��
	 * @return
	 */
	public InputStream getImgStream(String img){
		try {
			URL url = new URL(img);
			HttpURLConnection huc =(HttpURLConnection)url.openConnection();
			if(huc.getResponseCode() == 200){
				return huc.getInputStream();
			}
			throw new RuntimeException("��ȡͼƬ�쳣");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
