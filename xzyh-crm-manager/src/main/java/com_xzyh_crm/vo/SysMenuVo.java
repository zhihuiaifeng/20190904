package com_xzyh_crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com_xzyh_crm.pojo.SysMenu;
import com_xzyh_crm.util.longToString.LongJsonSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
public class SysMenuVo {
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;
    private Long modelId;
    /*父级的id*/
    private Long pid;

    private String pids;
    /*菜单名字*/
    private String menuName;
    /*菜单的路径*/
    private String menuUrl;

    private String menuIcon;

    private Integer menuStatus;

    private Integer menuSort;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer isLeaf;

    /*操作人的id*/
    private Long operatorId;

    // 非数据库字段
    private String parentName;
    // 非数据库字段
    private Integer level;
    // 非数据库字段
    private List<SysMenuVo> children;
}
