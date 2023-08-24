package com.rich.wechatrobot.model.qianxun.receive;

import lombok.Data;
import lombok.ToString;

/**
 * 接收消息
 *
 * @author AI
 * @email AI
 * @date 2023/7/27 16:54
 * @Copyright Copyright (c)  aulton Inc. All Rights Reserved.
 **/
@Data
@ToString
public class ReceiveMessage {

    /**
     * 事件的id（可用来区分是什么事件）
     */
    private String event;

    /**
     * 收到这条事件的微信
     */
    private String wxid;

    /**
     * 事件的主要内容，不同事件，具体对象参数也不尽相同
     */
    private ReceiveMessageData data;
}
