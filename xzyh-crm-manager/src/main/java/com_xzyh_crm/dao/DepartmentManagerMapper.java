package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.DepartmentManager;

public interface DepartmentManagerMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DepartmentManager record);

    int insertSelective(DepartmentManager record);

    DepartmentManager selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DepartmentManager record);

    int updateByPrimaryKey(DepartmentManager record);
}