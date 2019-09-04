package com_xzyh_crm.job;
 
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class SchedulerJob  {
	
	@Scheduled(cron="0 */1 * * * *") //每一分钟执行一次
	public void cancelSessionId() {
		//获取sessioi
		ServletRequestAttributes  requestAttributes  = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
		log.info("========定时任务==========session中"+requestAttributes.toString());
		//request.getSession().removeAttribute(PublicDictUtil.USER_TOKE);
		
	}

	 
}
