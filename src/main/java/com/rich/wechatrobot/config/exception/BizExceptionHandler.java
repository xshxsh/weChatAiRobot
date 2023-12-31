package com.rich.wechatrobot.config.exception;

import com.rich.wechatrobot.model.common.Response;
import com.rich.wechatrobot.model.common.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;

/**
 * @author: xsh
 * @create: 2021-06-15 17:20
 * @description: 全局异常拦截
 **/

@RestControllerAdvice
@Slf4j
public class BizExceptionHandler {

    /**
     * 通用异常校验
     */
    @ExceptionHandler(value = Exception.class)
    public Response<String> NormalExceptionHandler(Exception e) {
        log.error("发生异常:", e);
        if (e instanceof BizException) {
            return Response.fail(((BizException) e).getCode(), e.getMessage());
        }
        return Response.fail(e.getMessage() == null ? ResponseEnum.R_500.getMsg() : e.getMessage());
    }

    /**
     * 参数异常校验
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, BindException.class})
    public Response<String> methodArgumentNotValidException(Exception e) {
        log.error("参数异常:", e);
        BindingResult bindingResult;
        if (e instanceof MethodArgumentNotValidException) {
            bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
        } else {
            bindingResult = ((BindException) e).getBindingResult();
        }
        final ArrayList<String> errors = new ArrayList<>(10);
        bindingResult.getFieldErrors().forEach(
                fieldError -> errors.add(fieldError.getField() + ":" + fieldError.getDefaultMessage()));
        return Response.fail(ResponseEnum.R_1000.getCode(), StringUtils.collectionToCommaDelimitedString(errors));
    }
}