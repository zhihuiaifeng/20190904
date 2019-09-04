package com_xzyh_crm.util.fileUtil;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import com.github.benmanes.caffeine.cache.Cache;
import com_xzyh_crm.caffeine.CacheConfig;
import com_xzyh_crm.pojo.SysUser;
import com_xzyh_crm.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

 

/**
 * 获取当前用户
 * @author dell
 *
 */
@Component
public class GetCurrentUser {

	
	private static SysUserService userService;
	
	private static CacheConfig staticCacheConfig;
	
	@Autowired
	private SysUserService  service;
	
	@Autowired
	private CacheConfig cacheConfig;
	
	
	
	@PostConstruct 
	public void init() {
		userService = service;
		staticCacheConfig = cacheConfig;
	}
	
	public static SysUser getCurrentUser(HttpServletRequest request) {
		String token = request.getHeader("token");
		Cache<Object, Object> cache = staticCacheConfig.cacheToken();
		Object object = cache.getIfPresent(token);
		
		/*
		 * String userId = JWT.decode(token).getAudience().get(0); TUser user =
		 * userService.getByUserId(userId);
		 */
		if(object != null) {
			return (SysUser)object;
		}
		/*
		 * Object obj = request.getSession().getAttribute(PublicDictUtil.USER); if(obj
		 * != null) { return (TUser) obj; }
		 */
		return null;
	}
	
	/*public static String getToken(TUser user) {
		
        String token="";
        token= JWT.create().withAudience(user.getId())
                .sign(Algorithm.HMAC256(user.getUsername()));
        return token;
    }*/
}
