package cn.mirror6.rbac.response;

/**
 * @author mirror6
 * @description
 * @date 2019/8/26 11:21
 */
public enum ResultEnum {

    /**
     *
     */
    UNKNOWN_ERROR(-1, "未知错误"),
    SUCCESS(200, "成功"),
    NOT_FOUND(404,"未找到");

    /**
     * 成员变量：
     * 状态码
     * 返回信息
     */
    private Integer code;
    private String msg;

    /**
     * 构造函数
     */
    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 普通方法
     */
    public static String getMsg(int code) {
        for (ResultEnum ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMsg();
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


}
