package edu.ysu.backend.entity.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Result implements Serializable {
    private Integer code;
    private boolean success;
    private String msg;
    private Map<String, Object> data=new HashMap<>();
    private Result(){}
    public Result data(String key,Object value){
        this.data.put(key,value);
        return this;
    }
    public static Result ok(String message){
        Result result=new Result();
        result.setCode(200);
        result.setSuccess(true);
        result.setMsg(message);
        return result;
    }
    public static Result fail(String message){
        Result result=new Result();
        result.setCode(500);
        result.setSuccess(false);
        result.setMsg(message);
        return result;
    }
}
