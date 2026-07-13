package edu.ysu.backend.entity.common;

import lombok.Data;

@Data
public class CommonResult<T> {
    private String msg="ok";
    private int code=200;
    private T result;

    public CommonResult(T result){
        this.result=result;
    }

    public CommonResult(int code, String msg, T result) {
        this.code=code;
        this.msg=msg;
        this.result=result;
    }

    /**
     * 操作成功返回一个正向的值
     */
    public static<T> CommonResult<T> success(T result){
        return new CommonResult<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getmsg(),result);
    }
    public static<T> CommonResult<T> success(T result,int code){
        return new CommonResult<T>(code, ResultCode.SUCCESS.getmsg(),result);
    }

    /**
     * 失败返回结果
     * @param message
     * @return
     * @param <T>
     */

    public static <T> CommonResult<T> failed(String message) {
        return new CommonResult<T>(ResultCode.FAILED.getCode(), message, null);
    }

    public static <T> CommonResult<T> failed(IErrorCode errorCode) {
        return new CommonResult<T>(errorCode.getCode() , errorCode.getmsg(),  null);
    }
    public static <T> CommonResult<T> failed(T result) {
        return new CommonResult<T>(ResultCode.FINDNULL_LIST.getCode(),ResultCode.FINDNULL_LIST.getmsg(),result);
    }



}
