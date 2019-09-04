package com_xzyh_crm.controller;

import com_xzyh_crm.service.SysDepRoleService;
import com_xzyh_crm.service.SysRoleService;
import com_xzyh_crm.vo.DepartmentRoleVo;
import com_xzyh_crm.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Deprole")
public class SysDepRoleController {
    /*传入角色id，和type的类型，*/
    @Autowired
    private SysDepRoleService sysDepRoleService;

//    @PostMapping(value="/findDepRoles")
//    public Result findUserRoles(@RequestBody DepartmentRoleVo departmentRoleVo) {
//        return sysDepRoleService.findDepIdRoles(departmentRoleVo);
//    }

}
