package com_xzyh_crm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com_xzyh_crm.util.longToString.LongJsonSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysUser {

    private Long id;

    private String account;

    private String password;

    private String userName;

    private Integer age;

    private String address;

    private String phone;

    private String checkCode;

    private String salt;

    private Integer type;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private Long pid;

    private String pids;

    private String remark;

    private Integer isFirstLogin;

    private Integer isActive;

    private String province;

    private String city;

    private String area;

    private String contactName;

    private String contactPhone;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date otpOverTime;


    private Integer expireTime;

    private String userIcn;

    private Integer isShop;

    private String gwUrl;

    /**
     * 辅助字段
     */
    private List<Long> roleIds;
    /*
        0:禁止登陆  1:允许登录
     */
    private Integer isLogin;
    /*
        省市区
     */
    private List<String> Locations;
    /*
        起始时间和结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm" ,timezone = "GMT+8")
    private List<Date> dateMap;
}