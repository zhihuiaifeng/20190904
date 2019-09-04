package com_xzyh_crm.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com_xzyh_crm.filter.MyInterceptor;
import com_xzyh_crm.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.util.List;


@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private Config config;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
       String localpath= config.getUploadPath()+ config.getProductImg();
        String s1 = config.getProductImg().replaceAll("\\\\\\\\", "/");
        String s2 = config.getUploadPath().replaceAll("\\\\", "/");
        String[] split = config.getUploadPath().split("\\\\");
        String s3 = File.separator+split[2]+File.separator+s1+"**";
        String s = "file:"+localpath.replaceAll("\\\\\\\\", "/");
        String LinuxPath=config.getServerUrl();
        String os = System.getProperty("os.name");
        //如果是Windows系统
        if (os.toLowerCase().startsWith("win")) {
            registry.addResourceHandler("/xzyhImg/images/productImg/**")
                    //项目外路径file:H:/images/productImg/
                    .addResourceLocations(s);
        } else {  //linux 和mac
            registry.addResourceHandler("/images/**")
                    .addResourceLocations("file:/webapps/img");
        }
    }

    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myInterceptor).addPathPatterns("/**")
        .excludePathPatterns("/index.html");
    }



    /**
     * 设置跨域问题
     */
	/*@Override
	protected void addCorsMappings(CorsRegistry registry) {
		 registry.addMapping("/**")
		 		  .allowedOrigins("*")
		 		  .allowedMethods("GET","HEAD","POST","PUT","PATCH","DELETE","TRACE","OPTIONS")
		 		  .allowCredentials(true);
		super.addCorsMappings(registry);
	}*/


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        objectMapper.registerModule(simpleModule);
        jackson2HttpMessageConverter.setObjectMapper(objectMapper);
        converters.add(jackson2HttpMessageConverter);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);// 是否支持安全证书
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTION");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config); // 注册跨域
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

}
