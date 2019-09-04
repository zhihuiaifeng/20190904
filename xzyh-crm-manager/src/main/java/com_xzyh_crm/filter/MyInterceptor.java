package com_xzyh_crm.filter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;

import com.github.benmanes.caffeine.cache.Cache;
import com_xzyh_crm.annotion.Auth;
import com_xzyh_crm.caffeine.CacheConfig;
import com_xzyh_crm.exception.CustomException;
import com_xzyh_crm.util.PublicDictUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.alibaba.fastjson.JSON;



import lombok.extern.slf4j.Slf4j;

//@CrossOrigin(origins="*",maxAge=3600)
@Component
@Slf4j
public class MyInterceptor implements HandlerInterceptor {

	@Autowired
	private CacheConfig cacheConfig;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		/*
		 * response.setContentType("application/json;charset=utf-8");
		 * response.setCharacterEncoding("UTF-8");
		 * response.setHeader("Access-Control-Allow-Credentials", "true");
		 * response.setHeader("Access-Control-Allow-Origin",
		 * request.getHeader("Origin"));
		 * response.setHeader("Access-Control-Allow-Headers",
		 * "Origin, X-Requested-With, Content-Type, Accept");
		 * response.setHeader("Access-Control-Allow-Methods",
		 * "GET, HEAD, POST, PUT, DELETE, TRACE, OPTIONS, PATCH");
		 * response.setHeader("P3P", "CP=CAO PSA OUR");
		 */

		if (!handler.getClass().isAssignableFrom(HandlerMethod.class)) {
			return true;
		}
		final HandlerMethod handlerMethod = (HandlerMethod) handler;
		final Method method = handlerMethod.getMethod();
		final Class<?> clazz = method.getDeclaringClass();
		Cache<Object, Object> cache = cacheConfig.cacheToken();
		log.info("============handler传过来的token为:"
				+ request.getHeader("token"));

		if (clazz.isAnnotationPresent(Auth.class) || method.isAnnotationPresent(Auth.class)) {
			if (StringUtils.hasLength(request.getHeader("token"))) {
				//Object obj = request.getSession().getAttribute(PublicDictUtil.USER_TOKE);
				Object obj = cache.getIfPresent(request.getHeader("token"));
				if (obj != null) {
					log.info("token有效，开始调用");
					return true;
					//String sessid = (String) obj;
					/*
					 * if (cache.getIfPresent(PublicDictUtil.USER_TOKE).equals(request.getHeader(
					 * "token"))) { log.info("token有效，开始调用"); return true; }
					 */
				}

			}
			/*
			 * resultMap.put(PublicDictUtil.KEY, PublicDictUtil.LOGIN_ERRO_VALUE);
			 * resultMap.put(PublicDictUtil.MSG_KEY, "登录失效，请重新登录"); log.info("登录失效，请重新登录");
			 * String json = JSON.toJSONString(resultMap);
			 */
			//response.getWriter().print(new String(json.getBytes("UTF-8")));
			throw new CustomException("登录失效，请重新登录", PublicDictUtil.TOKEN_INVAALID);
			//return false;
		}

		return true;
	}

	/**
	 * 检索所有Cookie封装到Map集合
	 * 
	 * @param request
	 * @return
	 */
	public static Map<String, String> readCookieMap(HttpServletRequest request) {
		Map<String, String> cookieMap = new HashMap<String, String>();
		Cookie[] cookies = request.getCookies();
		cookieMap.put("token", cookies[0].getValue());
		/*
		 * if (null != cookies) { for (Cookie cookie : cookies) {
		 * cookieMap.put(cookie.getName(), cookie.getValue()); } }
		 */
		return cookieMap;
	}

	/*
	 * @Override public void postHandle(HttpServletRequest request,
	 * HttpServletResponse response, Object handler, ModelAndView modelAndView)
	 * throws Exception { // TODO Auto-generated method stub log.
	 * info("=======================进入拉拦截器的 postHandle 方法 ======================");
	 * HandlerInterceptor.super.postHandle(request, response, handler,
	 * modelAndView); }
	 */
}
