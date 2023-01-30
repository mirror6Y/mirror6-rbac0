package cn.mirror6.rbac.exception;

/**
 * Created on 2017/03/10
 *
 * @author annpeter.it@gmail.com
 */
public class CommonException extends RuntimeException {
    protected String errMsg;
    protected String detailMsg;
    protected CommonErrorCode error;
    protected Object data;

    public CommonException(CommonErrorCode error) {
        super(error.getMsg());
        this.error = error;
        this.errMsg = error.getMsg();
    }

    public CommonException(CommonErrorCode error, String errMsg) {
        super(errMsg);
        this.error = error;
        this.errMsg = errMsg;
    }

    public CommonException(CommonErrorCode error, String errMsg, String detailMsg) {
        super((detailMsg == null || detailMsg.isEmpty()) ? errMsg : detailMsg);
        this.error = error;
        this.errMsg = errMsg;
        this.detailMsg = detailMsg;
    }

    public CommonException(CommonErrorCode error, String errMsg, Throwable cause) {
        super(errMsg, cause);
        this.error = error;
        this.errMsg = errMsg;
    }

    public CommonException(CommonErrorCode error, String errMsg, String detailMsg, Throwable cause) {
        super((detailMsg == null || detailMsg.isEmpty()) ? errMsg : detailMsg, cause);
        this.error = error;
        this.errMsg = errMsg;
        this.detailMsg = detailMsg;
    }

    public CommonException(CommonErrorCode error, Throwable cause) {
        super(error.getMsg(), cause);
        this.error = error;
        this.errMsg = error.getMsg();
    }

    public String getErrMsg() {
        return errMsg;
    }

    public  CommonErrorCode getError() {
        return error;
    }

    public String getDetailMsg() {
        return detailMsg;
    }

    public void setDetailMsg(String detailMsg) {
        this.detailMsg = detailMsg;
    }

    public Object getData() {
        return data;
    }

    public CommonException setData(Object data) {
        this.data = data;
        return this;
    }
}
