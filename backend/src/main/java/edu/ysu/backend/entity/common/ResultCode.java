package edu.ysu.backend.entity.common;

public enum ResultCode implements IErrorCode{

    SUCCESS(200, "操作成功"),
    /**
     * 应该是多条记录，但是获得空
     */
    FINDNULL_LIST(407,"用户已存在"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数检验失败"),
    UNAUTHORIZED(401, "暂未登录或token已经过期"),
    FORBIDDEN(403, "没有相关权限");


    private ResultCode(int  code, String msg) {
        this.code = code;
        this.msg = msg;
    }


    private int code = 200;
    private String  msg = "OK";

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getmsg() {
        return msg;
    }

}
