package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.MenuRoleRef;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface MenuRoleRefMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MenuRoleRef record);

    int insertSelective(MenuRoleRef record);

    MenuRoleRef selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MenuRoleRef record);

    int updateByPrimaryKey(MenuRoleRef record);

    /*在菜单删除的时候进行查看中间表是否有关联*/
    List<MenuRoleRef> selectByMenuId(Long id);

    /*在菜单删除的时候进行查看中间表是否有关联*/
    List<MenuRoleRef> selectByButnId(Long id);
    /*批量删除*/
    int batchSaveUser(List<MenuRoleRef> record);
    /*删除操作，传入的为空*/
    int deleteByRoleID(@Param("param") HashMap map);
    /*删除操作，传入的按钮为空*/
    int deleteByBtnID(MenuRoleRef record);
    /*查询操作，在删除之前先看一下是否存在*/
    List<MenuRoleRef> selectByRoleTypeID(@Param("param") HashMap map);
}