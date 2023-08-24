package com.rich.wechatrobot.model.qianxun;

import lombok.Data;

/**
 * 千寻请求参数基类
 *
 * @author AI
 * @date 2023/7/15 17:46
 **/
@Data
public class QianXunBaseRequest<T> {

    /**
     * 指令
     */
    private String type;

    /**
     * 数据
     */
    private T data;
}
