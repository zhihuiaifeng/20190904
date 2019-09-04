package com_xzyh_crm.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com_xzyh_crm.util.longToString.LongJsonSerializer;
import lombok.Data;

@Data
public class DepartmentRoleVo {
    /*角色的ID*/
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long id;
    private Long departmentId;
    private Long roleId;
    private Long type;
}
