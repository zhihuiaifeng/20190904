package com_xzyh_crm.controller;

import com_xzyh_crm.exception.CustomException;
import com_xzyh_crm.pojo.SysMenu;
import com_xzyh_crm.service.SysMenuService;
import com_xzyh_crm.util.FormatUtil;
import com_xzyh_crm.util.PublicDictUtil;
import com_xzyh_crm.util.Result;
import com_xzyh_crm.vo.SysMenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.PUT;
import java.util.List;

@RestController
@RequestMapping("/SysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private FormatUtil formatUtil;

    /*菜单添加操作*/
    @PostMapping(value="/add")
    public Result save(SysMenuVo record, MultipartFile pImg, HttpServletRequest request) {
        if (!formatUtil.checkStringNull(record.getMenuName())){
            throw new CustomException("参数错误", PublicDictUtil.ERROR_VALUE);
        }
        /*先判断一下传入的对象是否为空*/
        return sysMenuService.save(record,pImg,request);
    }
    /*菜单修改操作操作*/
    @PutMapping (value="/edit")
    public Result edit(SysMenuVo record,MultipartFile pImg, HttpServletRequest request) {
        if (!formatUtil.checkStringNull(record.getMenuName())){
            throw new CustomException("参数错误", PublicDictUtil.ERROR_VALUE);
        }
        /*先判断一下传入的对象是否为空*/
        return sysMenuService.update(record,pImg,request);
    }

    @DeleteMapping(value="/delete")
    public Result delete(@RequestBody SysMenuVo records)
    {
        return sysMenuService.delete(records);
    }

//
//    @GetMapping(value="/findNavTree")
//    public HttpResult findNavTree(@RequestParam String userName) {
//        return HttpResult.ok(sysMenuService.findTree(userName, 1));
//    }

    /*获取菜单的操作*/
    @GetMapping(value="/findMenuTree")
    public Result findMenuTree() {

        return sysMenuService.findTree(null, 0);
    }

}
