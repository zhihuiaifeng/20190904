package com_xzyh_crm.service;
import com_xzyh_crm.dao.MenuBtnMapper;
import com_xzyh_crm.dao.MenuRoleRefMapper;
import com_xzyh_crm.exception.CustomException;
import com_xzyh_crm.pojo.*;
import com_xzyh_crm.util.PublicDictUtil;
import com_xzyh_crm.util.Result;
import com_xzyh_crm.util.StringUtils;
import com_xzyh_crm.util.Util;
import com_xzyh_crm.vo.SysBtnVo;
import com_xzyh_crm.vo.SysMenuVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class SysMenuBtnService {
    @Autowired
    private MenuBtnMapper menuBtnMapper;
    @Autowired
    private MenuRoleRefMapper menuRoleRefMapper;

    /*添加操作*/
    public Result save(SysBtnVo sysBtnVo){
        Long menuId = sysBtnVo.getMenuId();
        if(menuId == null) {
            throw new CustomException(PublicDictUtil.ERROR_VALUE,"父级菜单不能空");
        }
        /*拿到路径的地址去数据库查询一下*/
        MenuBtn byPerms = menuBtnMapper.findByPerms(sysBtnVo.getBtnClass());
        if (StringUtils.isNotNull(byPerms)){
            throw new CustomException(PublicDictUtil.ERROR_VALUE,"传入的路径已经存在");
        }
        sysBtnVo.setCreateTime(new Date());
        MenuBtn menuBtn = Util.copyProperties(sysBtnVo, MenuBtn.class);
        int count = menuBtnMapper.insertSelective(menuBtn);
        if (count > 0) {
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "修改成功");
        } else {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "修改失败");
        }
    }
    /*根据名字进行查询*/
    public MenuBtn findByName(String name){
       return menuBtnMapper.findByName(name);
    }
    public Result edit(SysBtnVo sysBtnVo) {
        /*拿到路径的地址去数据库查询一下*/
        MenuBtn byPerms = menuBtnMapper.findByPerms(sysBtnVo.getBtnClass());
        if (StringUtils.isNotNull(byPerms)){
            throw new CustomException(PublicDictUtil.ERROR_VALUE,"传入的路径已经存在");
        }
        MenuBtn menuBtn = Util.copyProperties(sysBtnVo, MenuBtn.class);
        int count = menuBtnMapper.updateByPrimaryKeySelective(menuBtn);
        if (count > 0) {
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "修改成功");
        } else {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "修改失败");
        }
    }

    /*获取全部的信息*/
    public Result  findAll() {
        log.info("获取全部的数据信息");
        /*获取全部的角色名*/
        List<MenuBtn> menuBtns = menuBtnMapper.selectAll();
//        返回数据列表
        if (StringUtils.isEmpty(menuBtns)) {
            throw new CustomException("查询失败(Query Failure)",PublicDictUtil.ERROR_VALUE);
        }
        /*这个是返回的消息,所有按钮的信息*/
        return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "查询成功", menuBtns);
    }
    /*删除操作*/
    public Result delete(Long id) {
        /*通过id查询一下中间表，看是否有人使用*/
        List<MenuRoleRef> menuRoleRefs = menuRoleRefMapper.selectByButnId(id);
        if (StringUtils.isEmpty(menuRoleRefs)){
            Result.resultData(PublicDictUtil.ERROR_VALUE, "按钮已分配,不允许删除");
        }
        int count = menuBtnMapper.deleteByPrimaryKey(id);
        if (count > 0) {
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "删除成功");
        } else {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "删除失败");
        }
    }

    /*通过一个按钮id获取按钮的信息*/
    public Result findById(Long id) {
        MenuBtn menuBtn = menuBtnMapper.selectByPrimaryKey(id);
        if (StringUtils.isNull(menuBtn)){
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "获取失败");
        }
        return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "获取成功",menuBtn);

    }
}
