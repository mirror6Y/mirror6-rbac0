package cn.mirror6.rbac.response;

/**
 * @author mirror6
 * @description
 * @date 2019/8/26 11:20
 */
public class ResponseFactory {

    /**
     * 公共私有静态函数
     *
     * @param code 响应码
     * @return Result json格式数据
     */
    private static Result commonBuild(int code, String msg) {
        Result result = new Result();
        result.setCode(code);
        if (msg == null || msg.trim().length() == 0) {
            result.setMsg(ResultEnum.getMsg(code));
        } else {
            result.setMsg(msg);
        }
        return result;
    }

    /**
     * 指定响应码-需预先在 @ResultEnum 里定义响应码
     * <pre>
     *     {
     *         "code":{code}
     *         "msg":{message}
     *     }
     * </pre>
     *
     * @param code 响应码
     * @return Result
     * @see ResultEnum
     */
    public static Result build(int code) {
        return commonBuild(code, ResultEnum.getMsg(code));
    }

    /**
     * 成功响应
     * <p>
     * <pre>
     * {
     *     "code":0,
     *     "msg":"success."
     * }
     * </pre>
     *
     * @return Result
     */
    public static Result build() {
        return commonBuild(ResultEnum.SUCCESS.getCode(), null);
    }

    /**
     * 成功响应
     * <pre>
     *     {
     *         "code":{code}
     *         "msg":{message}
     *     }
     * </pre>
     *
     * @param data 需要返回的数据对象
     * @return Result
     * @see ResultEnum
     */
    public static Result build(Object data) {
        Result json = commonBuild(ResultEnum.SUCCESS.getCode(), "请求成功");
        json.setData(data);
        return json;
    }

    /**
     * 自定义返回码code，构建返回数据
     *
     * @param code 响应码
     * @return Result
     */
    public static Result build(int code, Object data) {
        Result result = commonBuild(code, null);
        result.setData(data);
        return result;
    }

    /**
     * 自定义返回码code，构建返回数据
     *
     * @param code 响应码
     * @return Result
     */
    public static Result build(int code, String msg) {
        Result result = commonBuild(code, msg);
        result.setData(null);
        return result;
    }

    /**
     * 自定义返回码code，构建返回数据
     *
     * @param code 响应码
     * @param count
     * @param obj
     * @return Result
     */
    public static Result build(int code, int count, Object obj) {
        Result result = commonBuild(code, null);
        result.setCount(count);
        result.setData(obj);
        return result;
    }

    /**
     * 自定义返回码code，构建返回数据
     *
     * @param code 响应码
     * @return Result
     */
    public static Result build(int code, String msg, Object data) {
        Result result = commonBuild(code, msg);
        result.setData(data);
        return result;
    }

    /**
     * 自定义返回码code，构建返回数据
     *
     * @param code 响应码
     * @return Result
     */
    public static Result build(int code, int count, String msg, Object data) {
        Result result = commonBuild(code, msg);
        result.setData(data);
        result.setCount(count);
        return result;
    }
}
