package com.mcgj.web.filter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mcgj.entity.BaseEntity;

public class MCGJFilter implements Filter{

	public void destroy() {
		
	}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain domain) throws IOException, ServletException {
		try{
			res.setCharacterEncoding("utf-8");

			res.setContentType("text/html;charset=utf-8");
			req.setCharacterEncoding("utf-8"); 
			HttpServletResponse response = (HttpServletResponse)res;
			HttpServletRequest request = (HttpServletRequest) req;
			String uri = request.getRequestURI();
			Map<String, String> params = getRequestParamMap(request);
            if (!params.isEmpty()) {
                uri = uri + "?";
            }
            Set<Entry<String, String>> paramsSet = params.entrySet();
            //ƴ���û�����
            for (Entry<String, String> entry : paramsSet) {
                String paramName = entry.getKey();
                String paramValue = entry.getValue();
                uri = uri + "&" + paramName + "=" + paramValue;
            }
            System.out.println(uri);
            response.setHeader("Access-Control-Allow-Origin","*");//��ӿ������Ȩ��
            response.setHeader("Access-Control-Allow-Credentials", "true");
//             ��Ӧͷ����  
//            response.setHeader("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials");  
//            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
//            response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT");
//            response.setHeader("Access-Control-Allow-Headers","x-requested-with,content-type");  //��Ӧͷ �밴���Լ�������ӡ�
//            String token = request.getHeader("Access-Control-Request-Method");
//            System.out.println("�����û���tokenΪ:"+token);
//            System.out.println("�����û����󡣡�����������������"+request.getHeader("Access-Control-Request-Headers"));
//            System.out.println(request.getHeader("Access-Control-Request-Headers"));
//           Enumeration<String> str =  request.getHeaderNames();
//           while(str.hasMoreElements()){
//        	   System.out.println("@#$$");
////        	   System.out.println(str.nextElement());
//           }
            //�û�����Ȩ���жϣ����޷���ȡ�û�session����ͨ��
            domain.doFilter(request, response);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	 public final static Map<String, String> getRequestParamMap(HttpServletRequest request) {
		 Map<String, String> requestParamsMap = new HashMap<String, String>();
		 	try{
		        Enumeration<String> e = request.getParameterNames();
		        while (e.hasMoreElements()) {
		            String param = e.nextElement();
		            String value = request.getParameter(param);
		            if (value != null) {
		                requestParamsMap.put(param, value);
		            }
		        }
		 	}catch(Exception e){
		 		e.printStackTrace();
		 	}
	        return requestParamsMap;
	    }
}
