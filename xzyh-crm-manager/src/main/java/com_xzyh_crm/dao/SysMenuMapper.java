package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.SysMenu;
import com_xzyh_crm.vo.SysMenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysMenu record);

    int insertSelective(SysMenuVo record);

    SysMenu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysMenuVo record);

    int updateByPrimaryKey(SysMenu record);
    /*传入同级别的名字和pid进行查询*/
   SysMenu selectByLevelName(SysMenuVo sysMenuVo);
   /*在增加二级菜单时进行的判断，*/
   SysMenu selectById(Long pid);
    /*在增加菜单时进行sort赋值操作，要通过pid进行查询的*/
   Integer selectByPid(Long pid);
    /*获取全部的资源信息*/
    List<SysMenu> findAll();

    /*通过用户名字进行获取*/
    List<SysMenu> findByUserName(String userName);

    /*这个是在判断修改的时候选中的父级是否为自己的子集，拿到的是子集的全部信息。*/
    List<SysMenu> selectEdit(Long id);

    /*传入角色的id获取全部的菜单*/
    List<SysMenu> findRoleMenus(@Param(value="roleId") Long roleId);

    /*查看自己下面是否还有子菜单*/
    List<SysMenu> selectDelect(Long id);

    /*通过角色和type进行查询*/
    List<SysMenu> findRoleMenus(@Param("param") HashMap map);

}