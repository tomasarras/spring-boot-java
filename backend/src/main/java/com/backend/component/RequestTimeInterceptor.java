package com.backend.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import com.backend.service.LogService;

@Component("requestTimeInterceptor")
public class RequestTimeInterceptor implements HandlerInterceptor {

	@Autowired
	@Qualifier("logService")
	private LogService logService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		request.setAttribute("startTime", System.currentTimeMillis());
		
		return HandlerInterceptor.super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		long startTime = (long) request.getAttribute("startTime");
		
		if (logService == null){
		    BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		    logService = (LogService) factory.getBean("logService");
		    logService.info(RequestTimeInterceptor.class, "Request url: '" + request.getRequestURL().toString() + "' in '" + (System.currentTimeMillis() - startTime) + "ms'");
		} else {
			logService.info(RequestTimeInterceptor.class, "Request url: '" + request.getRequestURL().toString() + "' in '" + (System.currentTimeMillis() - startTime) + "ms'");
		}

		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
