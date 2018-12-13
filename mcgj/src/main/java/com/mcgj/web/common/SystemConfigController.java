package com.mcgj.web.common;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mcgj.utils.SystemConfigUtil;

/**
 * ϵͳ���ÿ�����
 * @author �
 * @create_date 2018-12-05 21:24
 * @address �Ϸ�
 */
@Controller
@RequestMapping("/systemConfig")
public class SystemConfigController {
	
	@RequestMapping("/selectSystemConfigByKey")
	@ResponseBody
	public String selectSystemConfigByKey(String key){
		return SystemConfigUtil.getSystemConfigByKey(key);
	}
}
