package com.mcgj.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;

import org.apache.log4j.Logger;

/**
 * base64������
 * @author ad
 *
 */
public class Base64Util {
	
	Logger log = Logger.getLogger(Base64Util.class);
	
	/**
	 * �ַ���BASE64 ����
	 * @return
	 */
	public static String getStrBASE64(String str){
		return new sun.misc.BASE64Encoder().encode(str.getBytes()); // ����ı��뷽��  
	}
	
	/** 
     * ͼƬBASE64 ���� 
     *  
     * @author 
     */ 
	public static String getPicBASE64(String picPath) {  
        String content = null;  
        try {  
            FileInputStream fileForInput = new FileInputStream(picPath);  
            byte[] bytes = new byte[fileForInput.available()];  
            fileForInput.read(bytes);  
            content = new sun.misc.BASE64Encoder().encode(bytes); // ����ı��뷽��  
            fileForInput.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return content;  
    }  
	
	/** 
     * ��ͼƬBASE64 ���� 
     *  
     * @author 
     */
	public static void getPicFormatBASE64(String str, String picPath) {  
        try {  
            byte[] result = new sun.misc.BASE64Decoder().decodeBuffer(str  
                    .trim());  
            RandomAccessFile inOut = new RandomAccessFile(picPath, "rw"); // r,rw,rws,rwd  
            inOut.write(result);  
            inOut.close();  
        } catch (Exception e) {
            e.printStackTrace();  
        }  
    }  
	public static void main(String[] args) {
		String con = getPicBASE64("E://timg.jpg");
		System.out.println(con);
		getPicFormatBASE64(con, "E://timg2.jpg");
//		File file = new File("E://timg2.jpg");
		
	}
}
