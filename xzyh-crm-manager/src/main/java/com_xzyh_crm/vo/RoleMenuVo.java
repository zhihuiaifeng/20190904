package com_xzyh_crm.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com_xzyh_crm.util.longToString.LongJsonSerializer;
import lombok.Data;

import java.util.List;

/**
 * @Auther: Administrator
 * @Date: 2019/9/2 0002 20:22
 * @Description:
 */
@Data
public class RoleMenuVo {
    /*角色的ID*/
    @JsonSerialize(using = LongJsonSerializer.class)
    private Long roleId;
    /*菜单的*/
    private List<MenusRolesVo> leaderpers;

//    private List<MenusRolesVo> FollowPers;
}
