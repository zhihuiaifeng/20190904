package com_xzyh_crm.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com_xzyh_crm.util.longToString.LongJsonSerializer;
import lombok.Data;

import java.util.Date;

@Data
public class SysUserDeparVo {

    /*
        主键
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;
    /*
        0:主负责人 (只能有一个)  1:普通成员
     */
    private Integer type;
    /*
        用户id
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long sysUserId;
    /*
        部门id
     */
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long departmentId;
    /*
        创建人
     */
    private Long operatorId;
    /*
        创建时间
     */
    private Date createTime;
}
