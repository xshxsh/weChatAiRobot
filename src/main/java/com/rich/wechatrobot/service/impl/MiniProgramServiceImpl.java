package com.rich.wechatrobot.service.impl;

import com.rich.wechatrobot.service.MiniProgramService;
import com.rich.wechatrobot.service.xunfei.WebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 处理微信小程序的消息
 *
 * @author AI
 * @email AI
 * @date 2023/7/31 20:36
 
 **/
@Service
@Slf4j
public class MiniProgramServiceImpl implements MiniProgramService {

    @Autowired
    WebSocketClient webSocketClient;

    @Override
    public String receive(String message) {
        webSocketClient.send(message);
        return null;
    }
}
