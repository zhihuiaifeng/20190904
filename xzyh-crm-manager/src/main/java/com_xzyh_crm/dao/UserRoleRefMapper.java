package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.UserRoleRef;

public interface UserRoleRefMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleRef record);

    int insertSelective(UserRoleRef record);

    UserRoleRef selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoleRef record);

    int updateByPrimaryKey(UserRoleRef record);
}