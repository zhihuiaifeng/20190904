package com_xzyh_crm.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com_xzyh_crm.util.longToString.LongJsonSerializer;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SysBtnVo {
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;

    private String btnName;

    private String btnClass;
    private String btnIcon;
    /*这个是菜单id*/
    private Long menuId;
    private Integer menuStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
}
