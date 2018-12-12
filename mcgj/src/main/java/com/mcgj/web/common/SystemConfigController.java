package com.mcgj.web.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mcgj.service.ISystemConfigService;

/**
 * œµÕ≥≈‰÷√øÿ÷∆∆˜
 * @author —Ó≥ø
 * @create_date 2018-12-05 21:24
 * @address ∫œ∑ 
 */
@Controller
@RequestMapping("/systemConfig")
public class SystemConfigController {
	
	@Autowired
	private ISystemConfigService systemConfigService;
	
	public String getSystemConfigByKey(String key){
		
		return null;
	}
}
