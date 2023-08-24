package com.rich.wechatrobot.service.impl;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.rich.wechatrobot.config.exception.BizException;
import com.rich.wechatrobot.model.qianxun.QianXunBaseRequest;
import com.rich.wechatrobot.model.qianxun.SendTextMsgRequest;
import com.rich.wechatrobot.service.SendMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rich.wechatrobot.model.constant.QianXunUrl.QX_PORT;

/**
 * 发送消息
 *
 * @author AI
 * @date 2023/7/15 17:39
 **/
@Service
@Slf4j
public class SendMessageServiceImpl implements SendMessageService {


    /**
     * 发送文本消息
     *
     * @param sendTxtMessageRequest
     */
    @Override
    public void sendTxtMessage(SendTextMsgRequest sendTxtMessageRequest) {
        QianXunBaseRequest<SendTextMsgRequest> qianXunBaseRequest = new QianXunBaseRequest();
        qianXunBaseRequest.setType("Q0001");
        SendTextMsgRequest sendTextMsgRequest = new SendTextMsgRequest();
        sendTextMsgRequest.setWxid(sendTxtMessageRequest.getWxid());
        sendTextMsgRequest.setMsg(sendTxtMessageRequest.getMsg());
        qianXunBaseRequest.setData(sendTextMsgRequest);
        try (HttpResponse httpResponse = HttpUtil.createPost(QX_PORT).body(JSONUtil.toJsonStr(qianXunBaseRequest)).execute()) {
            BizException.throwIf(httpResponse == null || !httpResponse.isOk(), "发送微信消息异常");
        }
    }


}
