package com_xzyh_crm.controller;

import com_xzyh_crm.exception.CustomException;

import com_xzyh_crm.pojo.MenuBtn;

import com_xzyh_crm.service.SysMenuBtnService;
import com_xzyh_crm.util.FormatUtil;
import com_xzyh_crm.util.PublicDictUtil;
import com_xzyh_crm.util.Result;
import com_xzyh_crm.util.StringUtils;
import com_xzyh_crm.vo.SysBtnVo;
import com_xzyh_crm.vo.SysMenuVo;
import com_xzyh_crm.vo.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/SysBtn")
public class SysMenuBtnController {
    @Autowired
    private SysMenuBtnService sysBtnService;
    @Autowired
    private FormatUtil formatUtil;

    @PostMapping("add")
    public Result add(@RequestBody SysBtnVo sysBtnVo){
        if (!formatUtil.checkStringNull(sysBtnVo.getBtnName(),sysBtnVo.getBtnClass())){
            throw new CustomException("参数错误", PublicDictUtil.ERROR_VALUE);
        }
        /*查看按钮的名字是否存在*/
        MenuBtn byName = sysBtnService.findByName(sysBtnVo.getBtnName());
        if (StringUtils.isNotNull(byName)){
            throw new CustomException(PublicDictUtil.ERROR_VALUE,"按钮名"+sysBtnVo.getBtnName()+"已经存在");
        }
        return sysBtnService.save(sysBtnVo);
    }
    @PutMapping("edit")
    public Result edit(@RequestBody SysBtnVo sysBtnVo){
        /*查看传入的字段是否为空，因为前端只传入名字和描述两个字段,还有一个字段就是id*/
        if (!formatUtil.checkStringNull(sysBtnVo.getBtnName(), sysBtnVo.getBtnClass())){
            return Result.resultData(PublicDictUtil.ERROR_VALUE,"传入的参数不正确");
        }
        /*查看按钮的名字是否存在*/
        MenuBtn byName = sysBtnService.findByName(sysBtnVo.getBtnName());
        if (StringUtils.isNotNull(byName)){
            throw new CustomException(PublicDictUtil.ERROR_VALUE,"按钮名"+sysBtnVo.getBtnName()+"已经存在");
        }
        return sysBtnService.edit(sysBtnVo);
    }
    /*获取菜单的操作*/
    @GetMapping(value="/list")
    public Result findBtnList() {

        return sysBtnService.findAll();
    }
    /*删除按钮的操作*/
    @DeleteMapping(value="/delete")
    public Result delete(Long id) {
        return sysBtnService.delete(id);
    }

    /*修改的时候先去查一下，获取单个值*/
    @GetMapping(value="/findRole/{id}")
    public Result findRoleById(@PathVariable Long id) {
        /*判断传入的id是否存在*/
//        System.out.println(id.intValue());
        if (id == null||id.intValue()<=0){
            throw new CustomException("传入的id不存在",PublicDictUtil.ERROR_VALUE);
        }
        return sysBtnService.findById(id);
    }



}
