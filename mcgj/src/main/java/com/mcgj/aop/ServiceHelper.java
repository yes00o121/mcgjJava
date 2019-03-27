package com.mcgj.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class ServiceHelper {
	
	@Pointcut("execution(public * com.mcgj.service.*Service.*(..))")
	public void mapperPoint() {
		
	}
	
	@Around("mapperPoint()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		
		System.out.println("数据切面。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。");
		Object proceed = pjp.proceed();
		return proceed;
	}
}
