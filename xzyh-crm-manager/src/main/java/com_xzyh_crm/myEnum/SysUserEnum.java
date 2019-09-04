package com_xzyh_crm.myEnum;

public enum SysUserEnum {
    IS_SHOPS(1, "普通成员"),
    NO_SHOPS(2, "超级用户"),
    IS_USE(1, "启用"),
    NO_USE(0, "停用"),
    IS_FIRST_LOGIN(1, "第一次登陆"),
    NO_FIRST_LOGIN(0, "多次登陆"),
    IS_LOGIN(0, "禁止登陆"),
    NO_LOGIN(1, "允许登陆"),

    ;

    private Integer code;
    private String msg;

    SysUserEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    // 普通方法
    public static String getName(Integer code) {
        for (SysUserEnum u : SysUserEnum.values()) {
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