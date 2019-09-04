package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.SysModel;

public interface SysModelMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysModel record);

    int insertSelective(SysModel record);

    SysModel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysModel record);

    int updateByPrimaryKey(SysModel record);
}