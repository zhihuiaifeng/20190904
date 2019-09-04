package com_xzyh_crm.service;

import com_xzyh_crm.dao.SysUserDeparMapper;
import com_xzyh_crm.exception.CustomException;
import com_xzyh_crm.pojo.SysUser;
import com_xzyh_crm.pojo.SysUserDepar;
import com_xzyh_crm.util.PublicDictUtil;
import com_xzyh_crm.util.Result;
import com_xzyh_crm.util.Util;
import com_xzyh_crm.vo.SysUserDeparVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysUserDeparService {

    @Autowired
    private SysUserDeparMapper sysUserDeparMapper;


    /**
     * 插入角色部门表
     * @param sysUserDeparvo
     * @return
     */
    public Result insertSysUserRole (SysUserDeparVo sysUserDeparvo) {

        SysUserDepar sysUserDepar = Util.copyProperties(sysUserDeparvo, SysUserDepar.class);

        if (sysUserDepar.getType() != null) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "用户的角色未定义种类", null);
        }
        if (sysUserDepar.getSysUserId() != null) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "用户主键不能为空", null);
        }
        if (sysUserDepar.getDepartmentId() != null) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "用户主键不能为空", null);
        }

        int count = sysUserDeparMapper.insertSelective(sysUserDepar);

        if (count < 1) {
            throw new CustomException("插入用户部门关联表失败", PublicDictUtil.ERROR_VALUE);
        }

        return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "插入用户部门关联表成功", null);
    }

}
