package com_xzyh_crm.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties(prefix = "tox.config")
@Getter
@Setter
public class Config {

	private String serverUrl;//服务器rul

	private String uploadPath;//图片上传路径

	private String productImg;//商品图片


}
