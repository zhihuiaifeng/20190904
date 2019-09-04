package com_xzyh_crm.vo;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com_xzyh_crm.group.PageValidGroup;
import com_xzyh_crm.util.longToString.LongJsonSerializer;
import lombok.Data;
import lombok.NonNull;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SysRoleVo {
//    vo对象来接收从数据库查询到的结果然后返回到前端
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;
    private String roleName;
    private String roleDesc;
    private Integer roleStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date updateTime;
    private Long sysUserId;
    private Integer isLeader;
    private Long pid;
//    @NotNull
//    @Min(value = 1, message = "当前页数不能小于1",groups = { PageValidGroup.class})
//    private Integer pageNo;//当前第一页
//    /** 每页条数 */
//    @Min(value = 1, message = "每页条数不能小于1" ,groups = { PageValidGroup.class})
//    @Max(value = 50, message = "每页条数不能大于50",groups = { PageValidGroup.class})
//    private Integer pageSize;//每页条数



}