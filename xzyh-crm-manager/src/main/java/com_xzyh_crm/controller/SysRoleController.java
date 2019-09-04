package com_xzyh_crm.controller;
import com_xzyh_crm.dao.SysRoleMapper;
import com_xzyh_crm.exception.CustomException;
import com_xzyh_crm.group.PageValidGroup;
import com_xzyh_crm.group.RoleEditValidGroup;
import com_xzyh_crm.myEnum.UserEnum;
import com_xzyh_crm.pojo.SysRole;
import com_xzyh_crm.service.SysRoleService;
import com_xzyh_crm.util.FormatUtil;
import com_xzyh_crm.util.PublicDictUtil;
import com_xzyh_crm.util.Result;
import com_xzyh_crm.util.StringUtils;
import com_xzyh_crm.vo.RoleMenuVo;
import com_xzyh_crm.vo.SysRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/role")
@Slf4j
public class SysRoleController {
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private FormatUtil formatUtil;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    /*添加操作*/
//    @PostMapping("save")
//    public Result save(@RequestBody SysRoleAddVo sysRoleVo){
//        /*判断传入的参数*/
//        if (sysRoleVo.getLeaderListRoleVo().isEmpty()|| sysRoleVo.getLeaderListRoleVo().size()!=2) {
//            throw new CustomException("传入参数错误",PublicDictUtil.ERROR_VALUE);
//        }
//         return sysRoleService.save(sysRoleVo);
//
//    }
    @PostMapping("add")
    public Result add(@Validated(RoleEditValidGroup.class) @RequestBody SysRoleVo sysRoleVo){
        /*先用户名查一下对象*/
        SysRole byName = sysRoleService.findByName(sysRoleVo.getRoleName());
        if (StringUtils.isNotNull(byName)){
            throw new CustomException(PublicDictUtil.ERROR_VALUE,"角色名"+sysRoleVo.getRoleName()+"已经存在");
        }
        return sysRoleService.add(sysRoleVo);
    }
   /*查询获取角色的消息，这个是模糊查询的那种方式*/
    @PostMapping(value="/findAll")
    public Result findAll(@RequestParam String name) {
        if (StringUtils.isEmpty(name)) {
            throw new CustomException(PublicDictUtil.ERROR_VALUE,"传入的名字不能为空!");
        }
        return sysRoleService.findByRole(name);
    }
  /*  修改操作*/
    @RequestMapping("edit")
    public Result edit(@RequestBody SysRoleVo sysRoleVo){
        /*查看传入的字段是否为空，因为前端只传入名字和描述两个字段,还有一个字段就是id*/
        if (!formatUtil.checkStringNull(sysRoleVo.getRoleName(), sysRoleVo.getRoleDesc())){
            return Result.resultData(PublicDictUtil.ERROR_VALUE,"传入的参数不正确");
        }
        if(UserEnum.sys_SHOPS.getMsg().equalsIgnoreCase(sysRoleVo.getRoleName())) {
            throw new CustomException(PublicDictUtil.ERROR_VALUE,"超级管理员不允许修改!");
        }
        return sysRoleService.edit(sysRoleVo);
    }
    /*修改的时候先去查一下，有可能不使用的*/
    @PostMapping(value="/findRole")
    public Result findRoleById(@RequestParam Long id) {
        /*判断传入的id是否存在*/
        System.out.println(id.intValue());
        if (id == null||id.intValue()<=0){
            throw new CustomException("传入的id不存在",PublicDictUtil.ERROR_VALUE);
        }
        return sysRoleService.findById(id);
    }

    /*删除操作*/
    @DeleteMapping(value="/delete/{id}")
    public Result delete(@PathVariable Long id) {
        if (!formatUtil.checkLong(id)) {
            throw new CustomException("传入的ID参数错误",PublicDictUtil.ERROR_VALUE);
        }
        return sysRoleService.delete(id);
    }

    /*点击一个角色的时候，传入角色的ID为几,查出所有的菜单主的和从的*/
    @PostMapping(value = "/findRoleMenus/{roleId}")
    public Result findRoleMenus(@PathVariable Long roleId) {

        return sysRoleService.findRoleMenus(roleId);
    }
//    @GetMapping(value = "/find")
//    public Result findRoleMenus() {
//
//        return sysRoleService.findRoleMenus(1L);
//    }
    /*这个是保存角色和菜单表的关系。*/
    @PostMapping(value="/saveRoleMenus")
    public Result saveRoleMenus(@RequestBody RoleMenuVo records) {
        /*如果角色的ID为空，恶意提交*/
        if(!formatUtil.checkLong(records.getRoleId())){
            throw new CustomException("恶意提交，记录IP",PublicDictUtil.ERROR_VALUE);
        }
        if (records.getLeaderpers().isEmpty()) {
            throw new CustomException("角色授权参数错误",PublicDictUtil.ERROR_VALUE);
        }
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(records.getRoleId());
        if (UserEnum.sys_SHOPS.getMsg().equalsIgnoreCase(sysRole.getRoleName())) {
            // 如果是超级管理员，不允许修改
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "超级管理员拥有所有菜单权限，不允许修改！");
        }
        return sysRoleService.saveRoleMenus(records);
    }


}
