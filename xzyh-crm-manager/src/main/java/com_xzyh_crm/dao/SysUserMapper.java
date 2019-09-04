package com_xzyh_crm.dao;

import com_xzyh_crm.pojo.SysUser;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 查询用户
     * @param user
     * @return
     */
    SysUser findUserByParam(SysUser user);

    /**
     * 查询手机号
     * @param sysUser
     * @return
     */
    SysUser findPhoneIsExist(SysUser sysUser);
}