package com_xzyh_crm.vo;

import lombok.Data;

import java.util.List;


@Data
public class MenusRolesVo {
    private Integer type;
    private List<String> menids;
    private List<String> btnids;
}
