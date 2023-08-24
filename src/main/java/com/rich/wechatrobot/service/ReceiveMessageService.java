package com.rich.wechatrobot.service;

import com.rich.wechatrobot.model.qianxun.receive.ReceiveMessage;

/**
 * 微信接收消息
 *
 * @author AI
 * @date 2023/8/9 21:37
 **/

public interface ReceiveMessageService {

    /**
     * 接收微信消息
     *
     * @param receiveMessage
     */
    void receiveMessage(ReceiveMessage receiveMessage);
}
