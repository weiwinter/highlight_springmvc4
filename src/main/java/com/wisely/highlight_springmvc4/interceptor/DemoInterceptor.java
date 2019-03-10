package com.wisely.highlight_springmvc4.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class DemoInterceptor extends HandlerInterceptorAdapter {
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
	        throws Exception
    {
		Long startTime = System.currentTimeMillis();
		request.setAttribute("startTime", startTime);
        return true;
    }

    public void postHandle(HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, Object obj, ModelAndView modelandview)
        throws Exception
    {
    	Long startTime = (Long) httpservletrequest.getAttribute("startTime");
    	Long endTime = System.currentTimeMillis();
    	httpservletrequest.removeAttribute("startTime");
    	System.out.println("---请求处理时间="+(endTime - startTime));
    	httpservletrequest.setAttribute("handleTime", endTime - startTime);
    }

}
