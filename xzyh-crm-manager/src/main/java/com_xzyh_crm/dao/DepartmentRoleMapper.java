package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.DepartmentRole;

import java.util.List;

public interface DepartmentRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DepartmentRole record);

    int insertSelective(DepartmentRole record);

    DepartmentRole selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DepartmentRole record);

    int updateByPrimaryKey(DepartmentRole record);

    /*传入角色id进行查询是否有关联*/
    List<DepartmentRole> selectByRoleId(Long id);
}