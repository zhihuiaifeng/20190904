package com_xzyh_crm.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.collect.Maps;
import com_xzyh_crm.caffeine.CacheConfig;
import com_xzyh_crm.dao.SysUserMapper;
import com_xzyh_crm.exception.CustomException;
import com_xzyh_crm.myEnum.SysUserEnum;
import com_xzyh_crm.myEnum.UserEnum;
import com_xzyh_crm.pojo.SysUser;
import com_xzyh_crm.pojo.SysUserDepar;
import com_xzyh_crm.util.*;
import com_xzyh_crm.util.fileUtil.GetCurrentUser;
import com_xzyh_crm.vo.SysUserDeparVo;
import com_xzyh_crm.vo.SysUserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class SysUserService {
    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private CacheConfig cacheConfig;

    @Autowired
    private SysUserDeparService sysUserDeparService;

    public Result<Map<String, String>> login(SysUser user, HttpServletRequest request, HttpServletResponse response) throws Exception{
        // 账号不能为空
        if (StringUtils.isBlank(user.getAccount())) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "账号不能为空", null);
        }
        String paramPass = user.getPassword();
        SysUser currentUser = sysUserMapper.findUserByParam(user);

        if (currentUser == null) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "该用户不存在", null);
        }

            // 判断密码是否正确
            if (!currentUser.getPassword().equals(Coder.encodeMD5(user.getPassword(), currentUser.getSalt()))) {
                return Result.resultData(PublicDictUtil.ERROR_VALUE, "密码不正确", null);
            }

        Map<String, String> resultMap = Maps.newHashMap();


            // 商户
            // 判断用户到期状态

            if (new Date().getTime() < currentUser.getStartTime().getTime()) {
                return Result.resultData(PublicDictUtil.ERROR_VALUE,
                        "您的开始使用时间为:" + DateUtil.getStrHms(currentUser.getStartTime()), null);
            }

            if (new Date().getTime() > currentUser.getEndTime().getTime()) {
                return Result.resultData(PublicDictUtil.ERROR_VALUE,
                        "您的登录账号已过期,过期时间为:" + DateUtil.getStrHms(currentUser.getEndTime()), null);
            }

          /*  // 判断用户是否是第一次登录
            if (currentUser.getIsFirstLogin() == UserEnum.IS_FIRST_LOGIN.getCode()) {
                resultMap.put("id", currentUser.getId());
                return Result.resultData(PublicDictUtil.ISFIRSTLOGIN, "第一次登录,请修改密码", resultMap);
            }*/


        // 判断用户是否被停用
        if (currentUser.getStatus() == UserEnum.N_STATUS.getCode()) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "抱歉,您的账号已经被停用", null);
        }


        Cache<Object, Object> cache = cacheConfig.cacheToken();
        String token = System.currentTimeMillis() + RandomUtils.getRandomStr(6, 2);
        cache.put(token, currentUser);
        cache.put(PublicDictUtil.USER_TOKE, token);
        log.info("========================当前token为:" + cache.getIfPresent(PublicDictUtil.USER_TOKE));

        resultMap.put(PublicDictUtil.USER_TOKE, token);
        resultMap.put("name", currentUser.getUserName());
        resultMap.put("userId", String.valueOf(currentUser.getId()));
        resultMap.put("userType", currentUser.getIsShop().toString());

        return Result.resultData(PublicDictUtil.SUCCESS_VALUE, null, resultMap);
    }

    public Result registerUser(SysUser user,HttpServletRequest request) {
      /*  if (user.getRoleIds().size() <= 0 || user.getRoleIds().isEmpty()) {
           return Result.resultData(PublicDictUtil.ERROR_VALUE, "至少选择一个角色名称", null);
        }
        //获取当前用户
        SysUserEnum currentUser = GetCurrentUser.getCurrentUser(request);
        if(org.springframework.util.StringUtils.isEmpty(currentUser)){
            throw new CustomException(PublicDictUtil.ERROR_VALUE,"登录失效,请重新登录!");
        }

        if(currentUser.getIsShop() == UserEnum.sys_SHOPS.getCode()){ //2 超级用户 创建商户
            user.setIsShop(UserEnum.IS_SHOPS.getCode());
        }else if(currentUser.getIsShop() == UserEnum.IS_SHOPS.getCode()){ //1:商户 创建商户下的人员
            user.setIsShop(UserEnum.IS_SHOPS.getCode());
        }else {
            //商户创建的人,  创建小组 给小组加人 任命组长
        }


        user.setIsShops(UserEnum.NO_SHOPS.getCode());
        // 判断用户的账号是否存在
        TUser oldUser = tUserMapper.findUserIsExis(user);
        if (oldUser != null) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "账号:" + user.getUsername() + "已经存在", null);
        }
        user.setId(Sid.nextShort());
        user.setIsFirstLogin(UserEnum.IS_FIRST_LOGIN.getCode());
        user.setCreateTime(new Date());
        TUser currentUser = GetCurrentUser.getCurrentUser(request);
        if (currentUser != null) {
            user.setCreateId(currentUser.getId());
        }
        //这个是设置启用状态
        user.setStatus(UserEnum.Y_STATUS.getCode());
//		设置的是盐
        user.setSalt(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + System.nanoTime());
//		设置密码
        user.setPassword(Coder.encodeMD5(user.getPassword(), user.getSalt()));
        int cont = tUserMapper.insertSelective(user);

        if (cont > 0) {
            // 添加权限
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "添加成功", null);
            *//*
             * cont = userPermissionRefMapper.batchInsert(user); if (cont > 0) { return
             * Result.resultData(PublicDictUtil.SUCCESS_VALUE, "添加成功", null); }
             *//*
        }
*/
        return Result.resultData(PublicDictUtil.ERROR_VALUE, "操作失败", null);
    }

    /**
     * 注册账号
     * @param user
     * @param request
     * @return
     */
    public Result insertSysUser(SysUserVO user, HttpServletRequest request) throws Exception {
        if (StringUtils.isBlank(user.getUserName())) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "用户名称为空", null);
        }
        if (StringUtils.isBlank(user.getAddress())) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "地址为空", null);
        }
        if (StringUtils.isBlank(user.getPhone())) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "手机号为空", null);
        }
        if (user.getType() == null) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "账户类型为空", null);
        }
        if (user.getSudType() == null) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "用户是否为负责人为空", null);
        }
        if (user.getIsLogin() == null) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "是否可以登陆为空", null);
        }
        if (user.getIsLogin() == SysUserEnum.NO_LOGIN.getCode() && StringUtils.isBlank(user.getAccount())) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "用户名不能为空", null);
        }
        if (user.getIsLogin() == SysUserEnum.NO_LOGIN.getCode() && StringUtils.isBlank(user.getAccount())) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "密码不能为空", null);
        }

        // 校验手机号码格式
        Pattern compile = Pattern.compile(PublicDictUtil.PHONE_NUMBER_REG);
        Matcher matcher = compile.matcher(user.getPhone());
        if (!matcher.matches()) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "手机号格式不能正确", null);
        }

        // 预留字段校验
        if (StringUtils.isNotBlank(user.getContactPhone())) {
            matcher = compile.matcher(user.getContactPhone());
            if (!matcher.matches()) {
                return Result.resultData(PublicDictUtil.ERROR_VALUE, "手机号格式不能正确", null);
            }
        }
//        if (StringUtils.isBlank(user.getContactName())) {
//            return Result.resultData(PublicDictUtil.ERROR_VALUE, "联系人姓名", null);
//        }
//        if (StringUtils.isBlank(user.getContactPhone())) {
//            return Result.resultData(PublicDictUtil.ERROR_VALUE, "联系人电话", null);
//        }

        SysUser sysUser = Util.copyProperties(user, SysUser.class);

        // 判断用户的账号是否存在
        SysUser oldUser = sysUserMapper.findUserByParam(sysUser);
        if (oldUser != null) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "账号:" + oldUser.getAccount() + "已经存在", null);
        }

        // 手机号加密
        sysUser.setPhone(Coder.encodeDES(sysUser.getPhone()));

        // 手机号去重
        SysUser phoneOldUser = sysUserMapper.findPhoneIsExist(sysUser);
        if (phoneOldUser != null) {
            return Result.resultData(PublicDictUtil.ERROR_VALUE, "该手机号："+Coder.decodeDES(sysUser.getPhone())+"已经存在", null);
        }

        //获取当前用户
        SysUser currentUser = GetCurrentUser.getCurrentUser(request);
        if(org.springframework.util.StringUtils.isEmpty(currentUser)){
            throw new CustomException(PublicDictUtil.ERROR_VALUE,"登录失效,请重新登录!");
        }

        // 密码加密
        if (user.getIsLogin() == SysUserEnum.NO_LOGIN.getCode()) {
            sysUser.setPassword(Coder.encodeMD5(sysUser.getPassword(), sysUser.getSalt()));
        } else {
            sysUser.setAccount(null);
            sysUser.setPassword(null);
        }

        // 预留字段
        if (StringUtils.isNotBlank(sysUser.getContactPhone())) {
            sysUser.setContactPhone(Coder.encodeDES(sysUser.getContactPhone()));
        }
        if (sysUser.getLocations() != null && !sysUser.getLocations().isEmpty()) {
            try {
                sysUser.setProvince(sysUser.getLocations().get(0));
                sysUser.setCity(sysUser.getLocations().get(1));
                sysUser.setArea(sysUser.getLocations().get(2));
            } catch (Exception e) {
                return Result.resultData(PublicDictUtil.ERROR_VALUE, "省市区赋值失败", null);
            }
        }
        if (sysUser.getDateMap() != null && sysUser.getDateMap().size() > 0) {
            // 判断起始时间早于结束时间
            if (!(sysUser.getDateMap().get(0).compareTo(sysUser.getDateMap().get(1)) < 0)) {
                return Result.resultData(PublicDictUtil.ERROR_VALUE, "起始日期晚于截至日期", null);
            }
            // 判断起始时间晚于今天
            try {
                Date paramTime = sysUser.getDateMap().get(0);

                if(paramTime != null){
                    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                    String strDate = sf.format(paramTime);
                    String newTime = strDate + "23:59:59";
                    paramTime = sf.parse(newTime);
                }
                if (!(DateUtil.getEndOrStartDate(new Date(), false).compareTo(paramTime) < 0)) {
                    return Result.resultData(PublicDictUtil.ERROR_VALUE, "起始日期不应早于今天", null);
                }
            } catch (Exception e) {
                return Result.resultData(PublicDictUtil.ERROR_VALUE, "时间转换异常", null);
            }
            // 设置商户起始时间和结束时间
            try {
                sysUser.setStartTime(sysUser.getDateMap().get(0));
                sysUser.setEndTime(sysUser.getDateMap().get(1));
                //这个是传入时间和过期时间
                int day = DateUtil.getDay(sysUser.getDateMap().get(0), sysUser.getDateMap().get(1));

                sysUser.setExpireTime(day);

            } catch (Exception e) {
                return Result.resultData(PublicDictUtil.ERROR_VALUE, "设置时间异常", null);
            }
        }

        // 初始化启用
        sysUser.setStatus(SysUserEnum.IS_USE.getCode());

        // 私有盐
        sysUser.setSalt(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase() + System.nanoTime());

        // 非超级用户
        sysUser.setIsShop(SysUserEnum.NO_SHOPS.getCode());

        // 创建时间
        sysUser.setCreateTime(new Date());

        // 设置pid和pids
        if (currentUser.getId() != null) {
            sysUser.setPid(currentUser.getId());
        }
        if (currentUser.getId() != null && currentUser.getPids() != null) {
            sysUser.setPids(currentUser.getId() + currentUser.getPids());
        }

        // 第一次登陆
        sysUser.setIsFirstLogin(SysUserEnum.IS_FIRST_LOGIN.getCode());

        // 插入用户数据
        int count = sysUserMapper.insertSelective(sysUser);

        if (count < 1) {
//            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "添加用户成功", null);
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "添加用户失败", null);
        }

        // 插入关联表数据
        SysUserDeparVo sysUserDeparvo = new SysUserDeparVo();
        sysUserDeparvo.setType(user.getSudType());
        sysUserDeparvo.setSysUserId(sysUser.getId());
        sysUserDeparvo.setDepartmentId(user.getDepartmentId());
        sysUserDeparvo.setCreateTime(new Date());
        sysUserDeparvo.setOperatorId(currentUser.getId());
        Result result = sysUserDeparService.insertSysUserRole(sysUserDeparvo);

        if (result.getCode().equals(PublicDictUtil.SUCCESS_VALUE)) {
            return Result.resultData(PublicDictUtil.SUCCESS_VALUE, "添加用户成功",null);
        } else {
            throw new CustomException("用户部门关联表插入失败", PublicDictUtil.SUCCESS_VALUE);
        }
    }
}
