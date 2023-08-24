package com.rich.wechatrobot.controller;

import com.rich.wechatrobot.model.common.Response;
import com.rich.wechatrobot.model.qianxun.SendTextMsgRequest;
import com.rich.wechatrobot.service.SendMessageService;
import com.rich.wechatrobot.service.xunfei.XunFeiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 发送消息
 *
 * @author AI
 * @date 2023/7/15 17:38
 **/
@RestController
@Api(tags = "发送微信消息")
@RequestMapping(value = "/api/v1.0/sendMsg")
public class SendMessageController {

    @Autowired
    SendMessageService sendMessageService;

    @Autowired
    XunFeiService xunFeiService;


    @ApiOperation(value = "发送文本消息")
    @PostMapping(value = "/txt")
    public Response sendTxtMessage(@RequestBody SendTextMsgRequest sendTxtMessageRequest) {
        sendMessageService.sendTxtMessage(sendTxtMessageRequest);
        return Response.success();
    }

    @ApiOperation(value = "发送AI")
    @GetMapping(value = "/ai")
    public Response sendToAi(@ApiParam("问题") @RequestParam("question") String question) {
        xunFeiService.sendToAi(question);
        return Response.success();
    }

}
