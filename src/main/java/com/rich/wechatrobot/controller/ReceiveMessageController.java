package com.rich.wechatrobot.controller;

import com.rich.wechatrobot.model.common.Response;
import com.rich.wechatrobot.model.qianxun.receive.ReceiveMessage;
import com.rich.wechatrobot.service.ReceiveMessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 接收消息
 *
 * @author AI
 * @date 2023/7/15 17:38
 **/
@RestController
@Api(tags = "微信接收消息")
@RequestMapping(value = "/api/v1.0/receiveMsg")
@Slf4j
public class ReceiveMessageController {

    @Autowired
    ReceiveMessageService receiveMessageService;


    @ApiOperation(value = "接收文本消息")
    @PostMapping(value = "/txt")
    public Response receiveMessage(@RequestBody ReceiveMessage receiveMessage) {
        receiveMessageService.receiveMessage(receiveMessage);
        return Response.success();
    }


}
