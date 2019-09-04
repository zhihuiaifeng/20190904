package com_xzyh_crm.myEnum;

/**
 * 用户枚举类型
 * @author dell
 *
 */

public enum UserEnum {
    IS_SHOPS(1, "商户"),
    NO_SHOPS(0, "商户下的人员"),
    sys_SHOPS(2,"超级管理员"),
    IS_FIRST_LOGIN(1, "第一次登录"),
    NO_FIRST_LOGIN(0, "登录一次以上"),
    is_active(0, "商户到期"),
    soon_active(2, "即将到期"),
    no_active(1, "商户未到期"),
    Y_STATUS(1, "启用"),
    N_STATUS(0, "停用"),
    IS_LEADER(1,"领导"),
    IS_FOLLOW(0,"从"),
    IS_LEADERPID(0,"领导的PID"),
    IS_MenuPids(0,"目录级的pids"),
    IS_Menu(1,"菜单"),
    IS_Btn(2,"按钮");

    private Integer code;
    private String msg;

    UserEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (UserEnum u : UserEnum.values()) {
            if (u.getCode() == code) {
                return u.msg;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}