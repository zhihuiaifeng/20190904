package com_xzyh_crm.service;

import com.github.pagehelper.PageHelper;
import com_xzyh_crm.dao.*;
import com_xzyh_crm.exception.CustomException;
import com_xzyh_crm.myEnum.UserEnum;
import com_xzyh_crm.pojo.*;
import com_xzyh_crm.util.*;
import com_xzyh_crm.vo.MenusRolesVo;
import com_xzyh_crm.vo.RoleMenuVo;
import com_xzyh_crm.vo.SysRoleAddVo;
import com_xzyh_crm.vo.SysRoleVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private FormatUtil formatUtil;
    @Autowired
    private DepartmentRoleMapper departmentRoleMapper;
    @Autowired
    private SysMenuService sysMenuService;
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private MenuBtnMapper menuBtnMapper;
    @Autowired
    private MenuRoleRefMapper menuRoleRefMapper;


    /*添加一起的角色*/
    public Result save(SysRoleAddVo sysRoleVo ) {
        SysRole leaderRole = new SysRole();
        SysRole followRole = new SysRole();
        /*获取到的角色进行进行遍历，拿到谁是主谁是从*/
       for (SysRoleVo roleVo : sysRoleVo.getLeaderListRoleVo()) {
           if (roleVo.getIsLeader()!=null) {
                /*这里存在疑问，前端是否传入pid还是自己生成的*/
               if (roleVo.getIsLeader().equals(UserEnum.IS_LEADER.getCode()) && roleVo.getPid().intValue() == (UserEnum.IS_LEADERPID.getCode())) {
                   BeanUtils.copyProperties(roleVo, leaderRole);
                  /* 存在疑问的leaderRole.setPid(UserEnum.IS_LEADER.getCode().longValue());*/
                   continue;
               } else {
                   BeanUtils.copyProperties(roleVo, followRole);
               }
           }
           else{
               throw new CustomException("参数错误",PublicDictUtil.ERROR_VALUE);
           }
        }
       /*这个判断传入的String字段是否为空*/
        if (!formatUtil.checkStringNull(leaderRole.getRoleName(),leaderRole.getRoleDesc(),followRole.getRoleName(),followRole.getRoleDesc())){
            throw new CustomException("参数错误",PublicDictUtil.ERROR_VALUE);
        }

        /*传完了值，进行查看一下，leader和follow中是否有数据*/
        if (StringUtils.isNull(leaderRole)&&StringUtils.isNull(followRole)){
            throw new CustomException("Leader传入的参数有问题",PublicDictUtil.ERROR_VALUE);
        }
        /*先leader用户名查一下对象*/
        SysRole byName = findByName(leaderRole.getRoleName());
        if (byName!=null){
            throw new CustomException("角色"+leaderRole.getRoleName()+"已经存在",PublicDictUtil.ERROR_VALUE);
        }
        /*创建时间*/
        leaderRole.setCreateTime(new Date());
        if (sysRoleMapper.insert(leaderRole)<0) {
           throw new CustomException(PublicDictUtil.ERROR_VALUE,"Leader角色插入失败");
        }
        /*获取刚才插入的数据*/
        SysRole byName1 = sysRoleMapper.findByName(leaderRole.getRoleName());
        followRole.setPid(byName1.getId());
        followRole.setCreateTime(new Date());
        /*插入从角色*/
        if (sysRoleMapper.insert(followRole)<0) {
            throw new CustomException("Follow角色插入失败",PublicDictUtil.ERROR_VALUE);
        }
        return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "添加成功", null);
    }
    /*添加角色*/
    public Result add(SysRoleVo sysRoleVo){
        if (!formatUtil.checkStringNull(sysRoleVo.getRoleName(),sysRoleVo.getRoleDesc())){
            throw new CustomException("参数错误",PublicDictUtil.ERROR_VALUE);
        }
        /*创建时给时间赋值操作*/
        sysRoleVo.setCreateTime(new Date());
        int count = sysRoleMapper.insertSelective(sysRoleVo);
        if (count > 0) {
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "添加成功");
        } else {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "添加失败");
        }
    }
    /*根据id进行数据库查询,修改的时候可能会使用*/
    public Result findById(Long id) {
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        if (StringUtils.isNull(sysRole)){
            throw new CustomException("查询失败(Query Failure)",PublicDictUtil.ERROR_VALUE);
        }
        return Result.resultData(PublicDictUtil.SUCCESS_VALUE,"查询成功",sysRole);
    }
    /*通过用户名进行查询*/
    public SysRole findByName(String name) {
        return sysRoleMapper.findByName(name);
    }
    /* 这个是添加角色时，如果是员工，判断一下领导是否存在*/
    public SysRole findByPid(Long id) {
        /*根据传入的id*/
        return sysRoleMapper.selectByPid(id);
    }
    /*获取全部的信息*/
    public Result  findAll() {
        log.info("获取全部的数据信息");
        /*获取全部的角色名*/
        List<SysRole> sysRoles = sysRoleMapper.selectAll();
//        返回数据列表
        if (StringUtils.isEmpty(sysRoles)) {
            throw new CustomException("查询失败(Query Failure)",PublicDictUtil.ERROR_VALUE);
        }
        /*这个是返回分页的消息*/
        return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "查询成功", sysRoles);
    }


    /*修改数据*/
    public Result edit(SysRoleVo sysRoleVo){
       /* 通过id进行查询原来的值*/
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysRoleVo.getId());
        /*如果传进来的数据和原有数据不同*/
        if (sysRoleVo.getRoleName().equals(sysRole.getRoleName()))
        {
            /*如果传入的角色名字，和查询的角色名字相同，没有更新角色名字*/
        }
        else {
            /*通过名字查数据库，看是否存在*/
            if(StringUtils.isNotNull(findByName(sysRoleVo.getRoleName())))
            {
                return Result.resultData(PublicDictUtil.ERROR_VALUE, "传入的名字已经存在", null);
            }
        }
        /*int i = sysRoleMapper.updateByPrimaryKeySelective(sysRole);*/
        sysRoleVo.setUpdateTime(new Date());
        BeanUtils.copyProperties(sysRoleVo, sysRole);
        int count = sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        if (count > 0) {
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "修改成功");
        } else {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "修改失败");
        }
    }

    /*删除角色*/
    public Result delete(Long id) {
        /*当删除的时候先看一下是否存在，*/
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(id);
        if (StringUtils.isNull(sysRole)){
            throw new CustomException("Query Failure参数异常",PublicDictUtil.ERROR_VALUE);
        }
        /*还要看一下部门中间表是否有关联*/
        List<DepartmentRole> departmentRoles = departmentRoleMapper.selectByRoleId(id);
        if (StringUtils.isNotEmpty(departmentRoles)) {
            throw new CustomException("当前角色被使用禁止删除",PublicDictUtil.ERROR_VALUE);
        }
        /*这个是假删除，设置为不可用状态*//*
        sysRoleMapper.updateByPrimaryKeySelective(sysRole1);
        /*这个是真删除，*/
        int count = sysRoleMapper.deleteByPrimaryKey(id);
        /*这个是假删除，设置为不可用状态*/
//        int count = sysRoleMapper.updateByPrimaryKeySelective(sysRole1);
        if (count > 0) {
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "删除成功");
        } else {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "删除失败");
        }
    }
    /*传入角色和typeid，查询出来菜单和按钮*/
    public Result findRoleMenus(Long roleId) {

            /*拿到角色的id，查出来所有的菜单*/
        SysRole sysRole = sysRoleMapper.selectByPrimaryKey(roleId);
        if(UserEnum.sys_SHOPS.getMsg().equalsIgnoreCase(sysRole.getRoleName())) {
            // 如果是超级管理员，返回全部,调用的是
             sysMenuService.findTree(null, 0);
        }
        /*将这两个结果都放入到map中返回给前端*/
        HashMap<String, Object> LeaderAndFollow = new HashMap<>();

        /*这个是主的数据*/
        /*通过角色id获取菜单表数据*/
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("roleId", roleId);
        map.put("type",1);
        List<SysMenu> roleMenus = sysMenuMapper.findRoleMenus(map);
        /*通过角色id获取按钮表数据*/
        List<MenuBtn> roleBtn = menuBtnMapper.findRoleBtn(map);
        /*将这两个数据进行合并回传给前端,这个是leader的*/
//        List<SysMenu> tree = findTree(roleMenus, 0, roleBtn);
        /*如果数据库中有数据，将数据进行三级操作，*/
        if (!StringUtils.isEmpty(roleMenus)) {
            /*这个是从的全部的菜单*/
            List<SysMenu> tree1 = findTree(roleMenus, 0, roleBtn);
            /*查询出来的数据进行添加入map*/
            if (StringUtils.isNotEmpty(tree1)) {
                LeaderAndFollow.put("Leader",tree1);
            }
        }


        /*这个是从的数据*/
        HashMap<String, Object> object = new HashMap<>();
        object.put("roleId", roleId);
        object.put("type",0);
        /*获取全部的菜单数据*/
        List<SysMenu> roleMenusFollow = sysMenuMapper.findRoleMenus(object);
        /*通过角色id获取按钮表数据*/
        List<MenuBtn> roleBtnFollow = menuBtnMapper.findRoleBtn(object);

        /*如果数据库中有数据，将数据进行三级操作，*/
        if (!StringUtils.isEmpty(roleMenusFollow)) {
            /*这个是从的全部的菜单*/
            List<SysMenu> tree1 = findTree(roleMenusFollow, 0, roleBtnFollow);
            /*查询出来的数据进行添加入map*/
            if (StringUtils.isNotEmpty(tree1)) {
                LeaderAndFollow.put("Follow",tree1);
            }
        }

        return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "查询成功", LeaderAndFollow);

    }

    /*获取菜单类型，null和0  0：获取所有菜单，包含按钮，1：获取所有菜单，不包含按钮 这个是返回值List<SysMenu>*/
//    获取全部 null, 0
    public List<SysMenu> findTree(List<SysMenu> roleMenus, int menuType,List<MenuBtn> roleBtn) {
        /*这个是用来装一级目录使用的*/
        List<SysMenu> sysMenus = new ArrayList<>();
        /*拿到了全部的资源信息，就是没有按钮表*/
//        List<SysMenu> menus = findByUser(userName);
        for (SysMenu menu : roleMenus) {
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
        findChildren(sysMenus, roleMenus, menuType, roleBtn);
        return sysMenus;

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
    private void findChildren(List<SysMenu> SysMenus, List<SysMenu> menus, int menuType,List<MenuBtn> roleBtn) {
        ArrayList<MenuBtn> menuBtns = new ArrayList<>();
        /*遍历一级目录*/
        for (SysMenu SysMenu : SysMenus) {
            List<SysMenu> children = new ArrayList<>();
//            /*遍历整个vo目录*/
            if (SysMenu.getLevel()==1){
//                Long id = SysMenu.getId();
//                List<MenuBtn> menuBtns = menuBtnMapper.selectByMenuId(id);
//                System.out.println("进数据库查询一下，添加到list中去");
                for (MenuBtn menuBtn : roleBtn) {
                    if (menuBtn.getMenuId().equals(SysMenu.getId())){
                        menuBtns.add(menuBtn);
                    }
                }
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
            findChildren(children, menus, menuType, roleBtn);
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

    /**/
    public Result saveRoleMenus(RoleMenuVo records) {
        /*拿到角色ID*/
        Long roleId = records.getRoleId();
        /*菜单角色中间表*/
        MenuRoleRef menuRoleRef = new MenuRoleRef();
        Integer type = records.getLeaderpers().get(0).getType();
        /*循环的进行遍历*/
        for (MenusRolesVo leaderPer : records.getLeaderpers()) {
            /*证明他是leader*/
            if (leaderPer.getType() == 1) {
                /*拿到所有的菜单ID*/
                List<String> menids = leaderPer.getMenids();
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("roleId", roleId);
                map.put("type", UserEnum.IS_LEADER.getCode());
                /*删之前也要查看一下这个数据是否存在*/
                List<MenuRoleRef> menuRoleRefs = menuRoleRefMapper.selectByRoleTypeID(map);
                if (StringUtils.isEmpty(menids)) {
                    if (menuRoleRefs.size() > 0) {
                        /*删除中间表中type为1的所有数据，就是没菜单,按钮也一定删了*/
                        int count = menuRoleRefMapper.deleteByRoleID(map);
                        if (count < 0) {
                            return Result.resultData(PublicDictUtil.ERROR_VALUE, "删除失败");
                        }
                        log.info("主表参数为空，删除原有菜单和按钮成功");
                    }
                    /*如果菜单为空，直接跳过按钮*/
                    continue;

                } else {
                    /*证明传入的菜单不为空，获取全部的菜单ID，先删除一下*/
                    /*删之前也要查看一下这个数据是否存在*/
//                    int RoleMenuCount = menuRoleRefMapper.selectByRoleTypeID(map);
                    /*证明有值的存在*/
                    if (menuRoleRefs.size() > 0) {
                        int count = menuRoleRefMapper.deleteByRoleID(map);
                        if (count < 0) {
                            return Result.resultData(PublicDictUtil.DELECT_MENUROLE_ERROR_VALUE, "删除失败");
                        }
                        log.info("主表参数不为空，删除原有菜单成功");
                    }
                    menuRoleRef.setSysRoleId(roleId);
//                        1:菜单 2:按钮
                    menuRoleRef.setSubjectType(UserEnum.IS_Menu.getCode());
                    /*1:主 2:从*/
                    menuRoleRef.setTypeId(UserEnum.IS_LEADER.getCode());

                    for (String menid : menids) {
                        /*插入数据库*/
                        menuRoleRef.setSubjectId(Long.valueOf(menid));
                        int i = menuRoleRefMapper.insertSelective(menuRoleRef);
                        if (i < 0) {
                            return Result.resultData(PublicDictUtil.ERROR_VALUE, "角色权限插入失败");
                        }
                    }
                    log.info("主表插入菜单成功");
                }


                /*上面菜单判断完了，到按钮了，拿到所有的按钮ID*/
                List<String> btnids = leaderPer.getBtnids();
                if (StringUtils.isEmpty(btnids)) {
//                    MenuRoleRef RoleBtn = new MenuRoleRef();
//                    RoleBtn.setSysRoleId(roleId);
////                        1:菜单 2:按钮
//                    RoleBtn.setSubjectType(UserEnum.IS_Btn.getCode());
//                    /*1:主 2:从*/
//                    RoleBtn.setType(UserEnum.IS_LEADER.getCode());
//                    /*当没有任何按钮的时候，删除中间表中所有角色按钮表的数据*/
//                    int count = menuRoleRefMapper.deleteByBtnID(RoleBtn);
//                    if (count > 0) {
//                        return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "按钮传入为空时,删除成功");
//                    }
                    continue;
                } else {
                    MenuRoleRef RoleBtn = new MenuRoleRef();
                    /*按钮插入数据库*/
                    RoleBtn.setSysRoleId(roleId);
//                        1:菜单 2:按钮
                    RoleBtn.setSubjectType(UserEnum.IS_Btn.getCode());
                    /*1:主 0:从*/
                    RoleBtn.setTypeId(UserEnum.IS_LEADER.getCode());
                    for (String btnid : btnids) {
                        RoleBtn.setSubjectId(Long.valueOf(btnid));
                        int i = menuRoleRefMapper.insertSelective(RoleBtn);
                        if (i < 0) {
                            return Result.resultData(PublicDictUtil.ERROR_VALUE, "主表按钮插入失败");
                        }
                    }
                    log.info("主表插入按钮成功");
                }

            }
            /*这里开始插入的是从的数据*/
            else {
                /*拿到所有的菜单ID*/
                List<String> menids = leaderPer.getMenids();
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("roleId", roleId);
                map.put("type",UserEnum.IS_FOLLOW.getCode());
                /*删之前也要查看一下这个数据是否存在*/
                List<MenuRoleRef> menuRoleRefs = menuRoleRefMapper.selectByRoleTypeID(map);
                if (StringUtils.isEmpty(menids)) {

                    if (menuRoleRefs.size() > 0) {
                        /*删除中间表中type为1的所有数据，就是没菜单,按钮也一定删了*/
                        int count = menuRoleRefMapper.deleteByRoleID(map);
                        if (count < 0){
                            return Result.resultData(PublicDictUtil.ERROR_VALUE, "删除失败");
                        }
                        log.info("传入菜单为空，从表删除菜单成功");
                    }
                    /*不在操作按钮*/
                    continue;
                } else {
                    if (menuRoleRefs.size() > 0) {
                        /*证明传入菜单不为空，获取全部的菜单ID，先删除一下*/
                        int count = menuRoleRefMapper.deleteByRoleID(map);
                        if (count < 0){
                            return Result.resultData(PublicDictUtil.ERROR_VALUE, "删除失败");
                        }
                        log.info("传入菜单不为空，从表删除菜单成功");
                    }
                    /*直接进行插入*/
                    menuRoleRef.setSysRoleId(roleId);
//                        1:菜单 2:按钮
                    menuRoleRef.setSubjectType(UserEnum.IS_Menu.getCode());
                    /*1:主 0:从*/
                    menuRoleRef.setTypeId(UserEnum.IS_FOLLOW.getCode());
                    /*循环的插入数据*/
                    for (String menid : menids) {
                        /*插入数据库*/
                        menuRoleRef.setSubjectId(Long.valueOf(menid));
                        int i = menuRoleRefMapper.insertSelective(menuRoleRef);
                        if (i < 0) {
                            return Result.resultData(PublicDictUtil.ERROR_VALUE, "角色权限插入失败");
                        }
                    }
                    log.info("从表插入菜单成功");

                }
                /*拿到所有的按钮ID*/
                List<String> btnids = leaderPer.getBtnids();
                if (StringUtils.isEmpty(btnids)) {
                    continue;
                } else {
                    MenuRoleRef RoleBtn = new MenuRoleRef();
                    /*按钮插入数据库*/
                    RoleBtn.setSysRoleId(roleId);
//                        1:菜单 2:按钮
                    RoleBtn.setSubjectType(UserEnum.IS_Btn.getCode());
                    /*1:主 2:从*/
                    RoleBtn.setTypeId(UserEnum.IS_FOLLOW.getCode());
                    for (String btnid : btnids) {
                        RoleBtn.setSubjectId(Long.valueOf(btnid));
                        int i = menuRoleRefMapper.insertSelective(RoleBtn);
                        if (i < 0) {
                            return Result.resultData(PublicDictUtil.ERROR_VALUE, "角色权限插入失败");
                        }
                    }
                    log.info("从表插入按钮成功");
                }
            }
        }
        return Result.resultData(PublicDictUtil.SUCCESS_VALUE,"插入成功");
    }
    /*模糊查询*/
    public Result findByRole(String name) {
        log.info("获取全部的数据信息");
        /*获取全部的角色名*/
        List<SysRole> sysRoles = sysRoleMapper.selectByRole(name);
////        返回数据列表
//        if (StringUtils.isEmpty(sysRoles)) {
//
//        }
        /*这个是返回分页的消息*/
        return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "查询成功", sysRoles);
    }
}
