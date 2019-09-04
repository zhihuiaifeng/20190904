package com_xzyh_crm.service;//package com_xzyh_crm.service;

import com_xzyh_crm.dao.MenuBtnMapper;
import com_xzyh_crm.dao.MenuRoleRefMapper;
import com_xzyh_crm.dao.SysMenuMapper;
import com_xzyh_crm.exception.CustomException;
import com_xzyh_crm.myEnum.UserEnum;
import com_xzyh_crm.pojo.MenuBtn;
import com_xzyh_crm.pojo.MenuRoleRef;
import com_xzyh_crm.pojo.SysMenu;
import com_xzyh_crm.pojo.SysUser;
import com_xzyh_crm.util.Config;
import com_xzyh_crm.util.PublicDictUtil;
import com_xzyh_crm.util.Result;
import com_xzyh_crm.util.StringUtils;
import com_xzyh_crm.util.fileUtil.GetCurrentUser;
import com_xzyh_crm.vo.SysMenuVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private MenuBtnMapper menuBtnMapper;
    @Autowired
    private MenuRoleRefMapper menuRoleRefMapper;
    @Autowired
    private CommonService commonService;
    @Autowired
    private Config config;

    /*添加操作*/
    public Result save(SysMenuVo record, MultipartFile pImg,HttpServletRequest request) {
        // seession 中获取操作用户信息
//        SysUser currentUser = GetCurrentUser.getCurrentUser(request);
//        if (currentUser == null) {
//            return Result.resultData(PublicDictUtil.ERROR_VALUE , "缓存获取用户失败");
//        }
//        Long id = currentUser.getId();
        /*如果拿到的pid为空说明他是目录级别的，如果拿到的pid不为空说明有父级，
        * 要拿到父级的id*/
        if(record.getPid() == null) {
            record.setPid(0L);
            /*如果父级为空，也说明这菜单为目录级别的*/
            record.setPids("0");
        }else {
            /*这个就是不是目录级别的，pid的值不能为空，并且它的pids需要去查询一下*/
            Long pid = record.getPid();
            /*获取到pid的值，当参数去查询，父级菜单信息*/
            SysMenu sysMenu = sysMenuMapper.selectById(record.getPid());
            if (StringUtils.isNull(sysMenu)) {
                throw new CustomException("传入的父级id不存在",PublicDictUtil.ERROR_VALUE);
            }
            String pids = sysMenu.getPids();
            /*这个是追加操作*/
            StringBuilder sb=new StringBuilder();
            sb.append(pids).append(",").append(sysMenu.getId());
            /*将拼接的字符串进行赋值操作*/
            record.setPids(sb.toString());
        }
        /*这个是查看当前插入的这条数据的相同pid下*/
        Long pid = record.getPid();
        Integer integer = sysMenuMapper.selectByPid(pid);
        /*如果通过pid查询出来的值不存在,或者为空则直接进行赋值*/
        if(integer==null||integer.equals("")){
            record.setMenuSort(0);
        }else {
            /*将原来的值进行加一操作*/

            int i = integer.intValue();
            i++;
            record.setMenuSort(i);
        }
        if(pImg != null) {
            //上传图片
            String tempData = commonService.uploadPhoto(pImg);
            if("no".equals(tempData) || "noType".equals(tempData)) {
                return Result.resultData(PublicDictUtil.ERROR_VALUE, "图片上传失败,请检查类型和网络", null);
            }else {

                /*设置图片的文件*/
               record.setMenuIcon(tempData);
            }
        }
        /*进行插入操作，要先去查询一下，当前名字在这个级别下是否有名字*/
        SysMenu sysMenu = sysMenuMapper.selectByLevelName(record);
        if (StringUtils.isNotNull(sysMenu)) {
            /*如果传入的同级别菜单的名字已经存在，报错*/
            throw new CustomException("菜单的名字已经存在",PublicDictUtil.ERROR_VALUE);
        }
        record.setCreateTime(new Date());
//        record.setOperatorId( currentUser.getId());
        /*进行插入处理*/
        return  sysMenuMapper.insertSelective(record)>0? Result.resultData(PublicDictUtil.SUCCESS_VALUE, "添加成功"):
                Result.resultData(PublicDictUtil.ERROR_VALUE, "添加失败");
    }


//    public int delete(SysMenu record) {
//        return sysMenuMapper.deleteByPrimaryKey(record.getId());
//    }
//
//
//    public int delete(List<SysMenu> records) {
//        for(SysMenu record:records) {
//            delete(record);
//        }
//        return 1;
//    }
//
//
//    public SysMenu findById(Long id) {
//        return sysMenuMapper.selectByPrimaryKey(id);
//    }
//
    /*这个是复杂的修改操作*/
    public Result edit(SysMenuVo record, HttpServletRequest request) {
        /*根据传入的id去数据库中查询一下*/
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(record.getId());

        if (record.getMenuName().equals(sysMenu.getMenuName())&&record.getPid().equals(sysMenu.getPid())){
            /*如果传入的角色名字，和查询的角色名字相同，没有更新角色名字,也要看一下是否换父类了*/
            /*如果这里为真，说明只改变了内容*/
        }
        else {
            /*传过来的数据id，去数据库中查询一下pid,拿到的是二级菜单，有可能为空*/
            List<SysMenu> sysMenus = sysMenuMapper.selectEdit(record.getId());
            if(StringUtils.isNotEmpty(sysMenus)){
                /*不为空则进行判断一下，*/
               sysMenus.forEach(SysMenu->{
                   if (record.getPid().intValue()==SysMenu.getId().intValue()){
                   throw new CustomException("父级不能修改成自己的子集",PublicDictUtil.ERROR_VALUE);
                   }
               }
            );
            }
            if (record.getPid().equals(sysMenu.getPid())) {
                throw new CustomException("父级id不能修改成自己",PublicDictUtil.ERROR_VALUE);
            }
            SysMenu sysMenulevel = sysMenuMapper.selectByLevelName(record);
            if (StringUtils.isNotNull(sysMenulevel)) {
                /*如果传入的同级别菜单的名字已经存在，报错*/
                throw new CustomException("菜单的名字已经存在",PublicDictUtil.ERROR_VALUE);
            }
        }

        /*父级已经变了，所以要更改一下pids 。通过名字查数据库，看是否存在，*/
//        这个是拿到父级的对象，
        SysMenu sysMenu1 = sysMenuMapper.selectById(record.getPid());
        /*这个是追加操作*/
        StringBuilder sb=new StringBuilder();
        sb.append(sysMenu1.getPids()).append(",").append(sysMenu1.getId());
        record.setPids(sb.toString());
        int i = sysMenuMapper.updateByPrimaryKeySelective(record);
        if (i > 0) {
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "修改成功");
        } else {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "修改失败");
        }
    }
    public Result update(SysMenuVo record,MultipartFile pImg, HttpServletRequest request) {
        /*根据传入的id去数据库中查询一下*/
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(record.getId());

        if (record.getMenuName().equals(sysMenu.getMenuName())){
            /*如果传入的角色名字，和查询的角色名字相同*/
        }
        else {
            SysMenu sysMenulevel = sysMenuMapper.selectByLevelName(record);
            if (StringUtils.isNotNull(sysMenulevel)) {
                /*如果传入的同级别菜单的名字已经存在，报错*/
                throw new CustomException("菜单的名字已经存在",PublicDictUtil.ERROR_VALUE);
            }
        }
        if(pImg != null) {
            //上传图片
            String tempData = commonService.uploadPhoto(pImg);
            if("no".equals(tempData) || "noType".equals(tempData)) {
                return Result.resultData(PublicDictUtil.ERROR_VALUE, "图片上传失败,请检查类型和网络", null);
            }else {
                /*设置图片的文件*/
                String allImgUrl = tempData.replaceAll("\\\\", "/");
                record.setMenuIcon(allImgUrl);
            }
        }
        int i = sysMenuMapper.updateByPrimaryKeySelective(record);
        if (i > 0&&pImg!=null) {
                //删除原来图片
                commonService.delFile(sysMenu.getMenuIcon());
                log.info("删除图片成功");
        }
        if(i>0){
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "修改成功");
        }else {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "修改失败");
        }
    }
    /*删除操作*/
    public Result delete(SysMenuVo record) {
        /*根据传入的id去数据库中查询一下*/
        SysMenu sysMenu = sysMenuMapper.selectByPrimaryKey(record.getId());
        /*看是否存在子菜单*/
        List<SysMenu> sysMenus = sysMenuMapper.selectDelect(record.getId());
        if (StringUtils.isEmpty(sysMenus)) {
            Result.resultData(PublicDictUtil.ERROR_VALUE, "存在子菜单,不允许删除");
        }
        List<MenuRoleRef> menuRoleRefs = menuRoleRefMapper.selectByMenuId(record.getId());
        if (StringUtils.isEmpty(menuRoleRefs)){
            Result.resultData(PublicDictUtil.ERROR_VALUE, "菜单已分配,不允许删除");
        }
        int count = sysMenuMapper.deleteByPrimaryKey(record.getId());
        if (count > 0) {
            //删除原来图片
            commonService.delFile(sysMenu.getMenuIcon());
            log.info("删除图片成功");
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "删除成功");
        } else {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "删除失败");
        }
    }

/*获取菜单类型，null和0  0：获取所有菜单，包含按钮，1：获取所有菜单，不包含按钮 这个是返回值List<SysMenu>*/
//    获取全部 null, 0
    public Result findTree(String userName, int menuType) {
         /*这个是用来装一级目录使用的*/
        List<SysMenu> sysMenus = new ArrayList<>();

        /*拿到了全部的资源信息，就是没有按钮表*/
        List<SysMenu> menus = findByUser(userName);
        for (SysMenu menu : menus) {
            /*将遍历出来的值进行复制操作*/
            /*如果得到的是目录一级*/
            if (menu.getPid() == null || menu.getPid() == 0) {
            /*设置等级为一级目录*/
                menu.setLevel(0);
                if (!exists(sysMenus, menu)) {
                    sysMenus.add(menu);
                }
        }
    }
        /*得到的是一级目录的几个消息,进行排序处理*/
        sysMenus.sort((o1, o2) -> o1.getMenuSort().compareTo(o2.getMenuSort()));
    findChildren(sysMenus, menus, menuType);
    if (StringUtils.isEmpty(sysMenus)) {
        return Result.resultData(PublicDictUtil.ERROR_VALUE, "查询失败");
    }
    return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "查询成功", sysMenus);
}
    /*获取全部，还是通过用户的名字进行获取*/
    public List<SysMenu> findByUser(String userName) {
        /*如果当前用户是超级管理员获取全部的消息*/
        if (userName == null || "".equals(userName) || UserEnum.sys_SHOPS.getMsg().equalsIgnoreCase(userName)) {
            return sysMenuMapper.findAll();
        }
        /*通过用户名获取全部的菜单*/
        return sysMenuMapper.findByUserName(userName);
    }
    /*第一个参数是一级目录，第二个参数是全部的数据*/
    private void findChildren(List<SysMenu> SysMenus, List<SysMenu> menus, int menuType) {
        /*遍历一级目录*/
        for (SysMenu SysMenu : SysMenus) {
           List<SysMenu> children = new ArrayList<>();
//            /*遍历整个vo目录*/
            if (SysMenu.getLevel()==1){
                Long id = SysMenu.getId();
                List<MenuBtn> menuBtns = menuBtnMapper.selectByMenuId(id);
                log.info("进数据库查询一下，添加到list中去");
                SysMenu.setChildren1(menuBtns);
            }
            for (SysMenu menu : menus) {
                if (menuType == 1) {
                    // 如果是获取类型不需要按钮，且菜单类型是按钮的，直接过滤掉
                    continue;
                }
                /*一级目录的ID等于二级目录的pid*/
                if (SysMenu.getId() != null && SysMenu.getId().equals(menu.getPid())) {
                    /*将一级目录的名字赋给二级目录*/
                    menu.setParentName(SysMenu.getMenuName());
                    menu.setLevel(SysMenu.getLevel() + 1);
                    if (!exists(children, menu)) {
                        /*children里面存的都是每个一级下面的二级*/
                        children.add(menu);
                    }
                }
            }
            /*将每个二级目录都存入了每个一级里面的List<children>*/
            SysMenu.setChildren(children);
            children.sort((o1, o2) -> o1.getMenuSort().compareTo(o2.getMenuSort()));
            /*循环多次，直到完成操作*/
            findChildren(children, menus, menuType);
        }
    }

    /*将一级目录进行过滤处理*/
    private boolean exists(List<SysMenu> sysMenus, SysMenu sysMenu) {
        boolean exist = false;
        /*第一次进来发现sysMenus为空*/
        for (SysMenu menu:sysMenus) {
            if(menu.getId().equals(sysMenu.getId())) {
                exist = true;
            }
        }
        return exist;
    }


}


