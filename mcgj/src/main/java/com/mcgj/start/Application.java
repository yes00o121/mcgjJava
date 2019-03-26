package com.mcgj.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * spring boot启动类
 * @author 杨晨
 * @date 2019-03-25	
 * @address 海口
 *
 */
@ComponentScan(basePackages={"com.mcgj"}) // 扫描该包路径下的所有spring组件	
@EnableAutoConfiguration
@MapperScan(value="com.mcgj.dao")//扫描mapper
public class Application extends SpringBootServletInitializer{
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
