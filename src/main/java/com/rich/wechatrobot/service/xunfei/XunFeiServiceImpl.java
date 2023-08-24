package com.rich.wechatrobot.service.xunfei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 讯飞AI交互
 *
 * @author AI
 * @date 2023/7/29 13:12
 **/
@Component
@Slf4j
public class XunFeiServiceImpl implements XunFeiService {

    @Autowired
    WebSocketClient webSocketClient;

    /**
     * 发送消息给AI
     */
    @Override
    public void sendToAi(String question) {
        webSocketClient.send(question);
    }

}
