package cn.mirror6.rbac.exception;

/**
 * @author tangtang
 * @date 2020/9/8  5:33 下午
 * @description:
 */
public enum CommonErrorCode {

    SUCCESS(200, "执行成功"),

    COLLECTION_IS_EMPTY(600, "集合为空,参数异常"),
    ARRAY_IS_EMPTY(601, "数组为空,参数异常");


    /**
     * 返回值
     */
    private Integer code;
    /**
     * 返回信息
     */
    private String msg;

    CommonErrorCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
