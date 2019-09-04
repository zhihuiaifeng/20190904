package com_xzyh_crm.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SysUserDepar {
    private Long id;

    private Integer type;

    private Long sysUserId;

    private Long departmentId;

    private Long operatorId;

    private Date createTime;

}