//package com_xzyh_crm.filter;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
//import org.springframework.web.filter.CorsFilter;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.module.SimpleModule;
//import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
//
//@Configuration
//public class MvcConfig extends WebMvcConfigurationSupport{
//
//	@Autowired
//	private MyInterceptor myInterceptor;
//
//	@Override
//	protected void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(myInterceptor).addPathPatterns("/**");
//		super.addInterceptors(registry);
//	}
//
//
//
//	/**
//	 * 设置跨域问题
//	 */
//	/*@Override
//	protected void addCorsMappings(CorsRegistry registry) {
//		 registry.addMapping("/**")
//		 		  .allowedOrigins("*")
//		 		  .allowedMethods("GET","HEAD","POST","PUT","PATCH","DELETE","TRACE","OPTIONS")
//		 		  .allowCredentials(true);
//		super.addCorsMappings(registry);
//	}*/
//
//
//	@Override
//	protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
//	    ObjectMapper objectMapper = new ObjectMapper();
//
//	    SimpleModule simpleModule = new SimpleModule();
//	    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
//	    simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
//	    objectMapper.registerModule(simpleModule);
//	    jackson2HttpMessageConverter.setObjectMapper(objectMapper);
//	    converters.add(jackson2HttpMessageConverter);
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//	public FilterRegistrationBean corsFilter() {
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);// 是否支持安全证书
//		config.addAllowedOrigin("*");
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("OPTION");
//		config.addAllowedMethod("GET");
//		config.addAllowedMethod("POST");
//		config.addAllowedMethod("PUT");
//		config.addAllowedMethod("HEAD");
//		config.addAllowedMethod("DELETE");
//		source.registerCorsConfiguration("/**", config); // 注册跨域
//		FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
//		bean.setOrder(0);
//		return bean;
//	}
//}
