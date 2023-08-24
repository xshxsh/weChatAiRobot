package com.rich.wechatrobot.service;

import com.rich.wechatrobot.model.qianxun.SendTextMsgRequest;

/**
 * 发送消息
 *
 * @author AI
 * @date 2023/7/15 17:39
 **/

public interface SendMessageService {

    /**
     * 发送文本消息
     *
     * @param sendTxtMessageRequest
     */
    void sendTxtMessage(SendTextMsgRequest sendTxtMessageRequest);

}
