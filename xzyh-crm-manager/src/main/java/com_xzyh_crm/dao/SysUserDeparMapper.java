package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.SysUserDepar;

public interface SysUserDeparMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUserDepar record);

    int insertSelective(SysUserDepar record);

    SysUserDepar selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUserDepar record);

    int updateByPrimaryKey(SysUserDepar record);
}