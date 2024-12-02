package com.cola.common.enums;

public enum SystemStateCodeEnum {
    /**
     * 请求成功
     */
    SUCCESS(0, "SUCCESS"),
    ERROR(1, "系统错误"),
    DATA_NOT_EXIST(2, "指定的数据不存在"),
    EDIT_FAILURE(3, "保存失败"),
    ERROR_PAGE_PARAM(4, "错误的分页参数"),
    ERROR_TOKEN_NULL(5, "用户token不能为空"),
    ERROR_ZERO_DIVIDE(6, "不能除以0"),
    ERROR_NEW_INSTANCE(7, "创建实例对象时，出现异常"),
    ERROR_CUSTOM_TIME_INTERVAL(8, "自定义时间范围必须设置开始时间和结束时间"),
    ERROR_REQUEST_TOO_MUCH(9, "请求过于频繁，请稍后再试"),
    ERROR_AES_SECRET_KEY_NULL(10, "aes密钥不能为空"),
    ERROR_ENCRYPTION(11, "加密异常"),
    ERROR_ENCRYPTION_UNSUPPORTED(12, "不支持的加密方式"),
    ERROR_REQUEST_ILLEGAL(13,"非法请求！"),
    ERROR_QUERY_DATE_INTERVAL(17, "查询时间间隔错误"),
    ERROR_QUERY_TYPE_NULL(18, "查询类型不能为空"),
    HTTP_SERVLET_REQUEST_NULL(19, "HttpServletRequest为空，无法获取请求头"),
    HTTP_PARAMETERS_MISS(20, "参数缺失"),
    PARAM_STRING_ERROR(21, "非法查询字符串"),


    /**
     * 短链分组管理
     */
    ERROR_DELETE_GROUP_BY_URL(30, "当前选中的分组下存在短链，删除失败！"),


    /**
     * 短网址管理
     */
    ERROR_URL_NULL(40, "输入的长链接不能为空！"),
    ERROR_URL_FORMAT(40,"请输入正确的http/https链接！"),


    /**
     * 艺术二维码
     */
    ERROR_NUM_LIMIT(50,"您的生成次数已为0，请及时充值！"),
    ERROR_GENERATE(51,"生成失败！");


    private int code;

    private String msg;

    SystemStateCodeEnum(int code, String msg) {

        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
