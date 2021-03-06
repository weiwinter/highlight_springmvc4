package com.wisely.highlight_springmvc4;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.wisely.highlight_springmvc4.interceptor.DemoInterceptor;
import com.wisely.highlight_springmvc4.messageconverter.MyMessageConverter;

@Configuration
@EnableWebMvc
@EnableScheduling
@ComponentScan("com.wisely.highlight_springmvc4")
public class MyMvcConfig extends WebMvcConfigurerAdapter{

	@Bean
	public InternalResourceViewResolver viewResolver(){
		InternalResourceViewResolver viewResolver = 
				new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/classes/views/");
		viewResolver.setSuffix(".jsp");
		viewResolver.setViewClass(JstlView.class);
		return viewResolver;
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry){
		
		registry.addResourceHandler("/assets/**").addResourceLocations("classpath:/assets/");
	}
	
	@Bean
	public DemoInterceptor getDemoInterceptor(){
		return new DemoInterceptor();
	}
	
	//重写方法，增加自定义拦截器
	public void addInterceptors(InterceptorRegistry interceptorregistry)
    {
		interceptorregistry.addInterceptor(getDemoInterceptor());
    }
	
	//页面转向 重写该方法实现代码简洁
	public void addViewControllers(ViewControllerRegistry registry){
		registry.addViewController("/index").setViewName("/index");
		registry.addViewController("/toUpload").setViewName("/upload");
		registry.addViewController("/converter").setViewName("/converter");
		//添加转向sse.jsp页面的映射
		registry.addViewController("/sse").setViewName("/sse");
		//添加转向async.jsp页面的影视
		registry.addViewController("/async").setViewName("/async");
	}
	
	//重写该方法  可以使项目不忽略“.”后面的参数
	public void configurePathMatch(PathMatchConfigurer configurer){
		configurer.setUseSuffixPatternMatch(false);
	}
	
	@Bean
	public MultipartResolver multipartResolver(){
		CommonsMultipartResolver multipartResolver = 
				new CommonsMultipartResolver();
		multipartResolver.setMaxUploadSize(1000000);
		multipartResolver.setDefaultEncoding("UTF-8");
		return multipartResolver;
	}
	
	//注册HttpMessageConverter
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters){
		converters.add(converter());
	}
	
	@Bean
	public MyMessageConverter converter(){
		return new MyMessageConverter();
	}
}
