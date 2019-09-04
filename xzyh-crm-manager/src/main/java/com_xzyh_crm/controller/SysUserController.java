package com_xzyh_crm.controller;

import com_xzyh_crm.annotion.Auth;
import com_xzyh_crm.pojo.SysUser;
import com_xzyh_crm.service.SysUserService;
import com_xzyh_crm.util.Result;
import com_xzyh_crm.vo.SysUserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 用户控制器
 */
@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/sysUser")
public class SysUserController {
    @Resource
    private SysUserService sysUserService;

    /**
     * 登录
     */
    @PostMapping("login")
    public Result<Map<String, String>> login(@RequestBody SysUser user, HttpServletRequest request, HttpServletResponse response)throws Exception{
        return sysUserService.login(user,request,response);
    }

    /**
     * 注册账号
     */
//    @PostMapping("add")
//    @Auth
//    public Result registerUser(@RequestBody SysUserEnum user,HttpServletRequest request){
//        return sysUserService.registerUser(user,request);
//    }


    /**
     * 添加账户信息
     * @param user
     * @param request
     * @return
     */
    @PostMapping("add")
    @Auth
    public Result insertSysUser (@RequestBody SysUserVO user, HttpServletRequest request) throws Exception {
        return sysUserService.insertSysUser(user, request);
    }

}
