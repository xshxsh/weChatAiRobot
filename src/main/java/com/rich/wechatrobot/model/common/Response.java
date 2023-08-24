package com.rich.wechatrobot.model.common;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author: xsh
 * @create: 2021-06-15 16:44
 * @description: 统一返回类
 **/

@Data
@ApiModel("统一响应")
public class Response<T> implements Serializable {
    //响应码
    private String code = ResponseEnum.R_200.getCode();
    //响应信息
    private String msg = ResponseEnum.R_200.getMsg();
    //响应内容
    private T data;

    public static Response<String> success() {
        return new Response<>();
    }

    public static <T> Response<T> success(T data) {
        final Response<T> response = new Response<>();
        response.setData(data);
        return response;
    }

    public static Response<String> fail(String msg) {
        final Response<String> response = new Response<>();
        response.setCode(ResponseEnum.R_500.getCode());
        response.setMsg(msg);
        return response;
    }

    public static <T> Response<T> fail(String code, String msg) {
        final Response<T> response = new Response<>();
        response.setCode(code);
        response.setMsg(msg);
        return response;
    }

}