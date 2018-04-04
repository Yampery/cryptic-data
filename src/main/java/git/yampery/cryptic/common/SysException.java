package git.yampery.cryptic.common;

/**
 * @decription SysException
 * <p>系统自定义异常<br/>
 * 说明：在可能存在运行时异常的地方进行捕获，获取异常信息，向上层抛出此异常<br/>
 * 在表现层进行捕获，并使用<tt>logger.error()</tt>打印日志<br/>
 * <p>异常码说明：{ 如果有自定义code，在此注明 }<br/>
 *      500：服务器内部错误，可能是系统自行抛出被捕获，也可能是明确的个人抛出的系统异常<br/>
 * <p/>
 * </p>
 * @author Yampery
 * @date 2018/4/4 17:15
 */
public class SysException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public SysException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public SysException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public SysException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public SysException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
