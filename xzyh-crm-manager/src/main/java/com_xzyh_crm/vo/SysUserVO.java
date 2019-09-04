package com_xzyh_crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com_xzyh_crm.util.longToString.LongJsonSerializer;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysUserVO {
    /*
        主键
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;
    /*
        账号
     */
    private String account;
    /*
        密码
     */
    private String password;
    /*
        用户名称
     */
    private String userName;
    /*
        年龄(预留)
     */
    private Integer age;
    /*
        地址
     */
    private String address;
    /*
        手机号
     */
    private String phone;
    /*
        1:后台  2:前台
     */
    private Integer type;
    /*
        0:停用  1:启用
     */
    private Integer status;
    /*
        创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    /*
        更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;
    /*
        父级id(预留)
     */
    private Long pid;
    /*
        所有id层级(预留)
     */
    private String pids;
    /*
        备注
     */
    private String remark;
    /*
        0:多次登录   1:第一次登录
     */
    private Integer isFirstLogin;
    /*
        0:已到期   1:未到期   2:即将到期(预留)
     */
    private Integer isActive;
    /*
        省(预留)
     */
    private String province;
    /*
        市(预留)
     */
    private String city;
    /*
        区(预留)
     */
    private String area;
    /*
        联系人姓名
     */
    private String contactName;
    /*
        联系电话
     */
    private String contactPhone;
    /*
        使用年限的启示时间(预留)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    /*
        使用年限的截止时间(预留)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    /*
        过期时间(手机专用)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date otpOverTime;
    /*
        到期天数(预留)
     */
    private Integer expireTime;
    /*
        用户头像图片名称(预留)
     */
    private String userIcn;
    /*
        商户的官网url(预留)
     */
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
    /*
        是否为该组管理者
     */
    private Integer sudType;
    /*
        部门id
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long departmentId;
}
