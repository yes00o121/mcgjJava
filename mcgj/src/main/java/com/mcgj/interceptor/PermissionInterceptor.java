package com.mcgj.interceptor;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Properties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.mcgj.entity.User;
import com.mcgj.redis.RedisHashUtil;
import com.mcgj.utils.MessageUtil;
import com.mcgj.utils.PropertiesUtil;
import com.mcgj.web.dto.ResultDTO;

/**
 * ������
 * 
 * @author �
 *
 */
public class PermissionInterceptor implements HandlerInterceptor {

	private static final Logger log = LoggerFactory
			.getLogger(PermissionInterceptor.class);

	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {

	}

	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object arg2) throws Exception {
		// ����ǲ����ؽӿ�ֱ��ͨ��
		String url = request.getServletPath();
		if (url.indexOf("select") != -1) {// ���Ϊ��ѯ�ӿڲ���������
			//�յ����������ж��û��Ƿ��¼�������¼�͸�����Ự����֪���û��Ƿ��Ծ����Ȼ��������
			updateConversation(request);
			return true;
		} else if ("/user/login".equals(url)) {// ��¼�ӿ�
			return true;
		} else if ("/user/register".equals(url)) {// ע��ӿ�
			return true;
		} else if ("/common/uploadFile".equals(url)) {// �ϴ��ļ��ӿ�
			return true;
		} else if ("/common/image".equals(url)) {// ��ѯͼƬ�ӿ�
			return true;
		} else if ("/common/generateVerification".equals(url)) {// ��֤��ӿ�
			return true;
		}else if ("/common/upNetWorkImg".equals(url)) {// �ϴ�����ͼƬ
			return true;
		}else if(url.indexOf("add") !=-1){
			//����������ݣ���ʱ������������ӵ�ϵͳ����
			return true;
		}
		//�����Ͻӿ�֮�������ӿڶ���Ҫ�û���¼����ܽ��в���
		return loginLegitimate(request, response);// �����û��ĵ�¼״̬
//		return true;
	}

	/**
	 * �ж��û���¼�Ƿ�Ϸ�
	 * 
	 * @return
	 */
	private Boolean loginLegitimate(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			response.setCharacterEncoding("UTF-8");  
		    response.setContentType("application/json; charset=UTF-8");  
			String token = request.getParameter("token");
			if (token == null || "".equals(token)) {
				sendLoginMessage(new PrintWriter(response.getOutputStream()));// ���ش�����Ϣ
				return false;
			}
			// �ж��û��Ự�Ƿ���ڻ��߹���
			Object record = RedisHashUtil.get(token);
			if (record == null) {
				sendOverTimeMessage(new PrintWriter(response.getOutputStream()));
				return false;
			}
			// �û�״̬�������»Ựʱ��
			RedisHashUtil.setex(token, (User) record,
					PropertiesUtil.getLoginDelay());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return false;
	}

	/**
	 * �û�û�е�¼������Ϣ
	 * 
	 * @return
	 */
	private void sendLoginMessage(PrintWriter pw) {
		try{
		ResultDTO result = new ResultDTO();
		result.setSuccess(false);
		result.setState(0);// ״̬Ϊfalse
		result.setMessage(new String(MessageUtil.MSG_NOT_LOGIN.getBytes("utf-8")));
		pw.append(JSONObject.toJSONString(result));
		pw.flush();
		pw.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}

	/**
	 * �Ự���ڴ�����Ϣ
	 * 
	 * @return
	 */
	private void sendOverTimeMessage(PrintWriter pw) {
		try{
			ResultDTO result = new ResultDTO();
			result.setSuccess(false);
			result.setState(2);// ״̬Ϊfalse
			result.setMessage(new String(MessageUtil.MSG_CONVERSATION_OVERTIME.getBytes("utf-8")));
//			pw.append(new String(JSONObject.toJSONString(result).getBytes("utf-8")));
			pw.append(JSONObject.toJSONString(result));
			pw.flush();
			pw.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e.getMessage());
		}
	}
	//�ж��û��Ƿ��¼�������¼���»Ự����Ȼ��������
	private void updateConversation(HttpServletRequest request){
		//��ȡtoken
		String token = request.getParameter("token");
		if(token != null){//�����е�¼��
			//�ж��û������Ƿ���ڣ������ڲ����в��������ڽ��и���
			Object record = RedisHashUtil.get(token);
			if(record != null){
				RedisHashUtil.setex(token, (User) record,
						PropertiesUtil.getLoginDelay());
			}
		}
	}
}
