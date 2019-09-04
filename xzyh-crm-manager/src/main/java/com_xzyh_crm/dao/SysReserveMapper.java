package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.SysReserve;

public interface SysReserveMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysReserve record);

    int insertSelective(SysReserve record);

    SysReserve selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysReserve record);

    int updateByPrimaryKey(SysReserve record);
}