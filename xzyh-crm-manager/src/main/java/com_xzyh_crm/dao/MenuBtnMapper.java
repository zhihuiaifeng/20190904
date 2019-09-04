package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.MenuBtn;
import com_xzyh_crm.pojo.SysRole;
import com_xzyh_crm.vo.SysBtnVo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

public interface MenuBtnMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MenuBtn record);
    /*插入按钮*/
    int insertSelective(MenuBtn menuBtn);

    MenuBtn selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MenuBtn record);

    int updateByPrimaryKey(MenuBtn record);

    /**/
    default List<MenuBtn> selectByMenuId(Long id) {
        return null;
    }

    /*添加按钮名字的时候，先查一下是否存在*/
    MenuBtn findByName(String name);

    /*查询数据库中，按钮的路径是否存在*/
    MenuBtn findByPerms(String name);

    /*这个是获取全部按钮信息*/
    List<MenuBtn> selectAll();
    /*通过角色的id获取的全部的按钮的信息*/
    List<MenuBtn> findRoleBtn(@Param("param") HashMap map);
}