package com.rich.wechatrobot.service.impl;

import com.rich.wechatrobot.model.qianxun.receive.ReceiveMessage;
import com.rich.wechatrobot.model.qianxun.receive.ReceiveMessageData;
import com.rich.wechatrobot.model.qianxun.receive.ReceiveMessageDataData;
import com.rich.wechatrobot.service.ReceiveMessageService;
import com.rich.wechatrobot.service.xunfei.WebSocketClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rich.wechatrobot.model.constant.CommonConstant.SAWADIKA;

/**
 * 微信接收消息
 *
 * @author AI
 * @date 2023/8/9 21:37
 **/
@Service
public class ReceiveMessageServiceImpl implements ReceiveMessageService {

    @Autowired
    WebSocketClient webSocketClient;


    /**
     * 接收微信消息
     */
    @Override
    public void receiveMessage(ReceiveMessage receiveMessage) {
        //        log.info("收到微信发来的消息:{}", JSONUtil.toJsonStr(receiveMessage));
        ReceiveMessageData data = receiveMessage.getData();
        ReceiveMessageDataData receiveMessageData = receiveMessage.getData().getData();
        if (receiveMessageData == null) {
            return;
        }
        // 只接收消息
        if (receiveMessageData.getMsgType() != 1) {
            return;
        }
        // 公众号消息
        if (receiveMessageData.getFromType() == 3) {
            return;
        }
        // 群聊非@我消息
        if (receiveMessageData.getFromType() == 2) {
            if (!receiveMessageData.getMsg().contains(SAWADIKA)) {
                return;
            }
        }

        String fromWxid = data.getData().getFromWxid();
        String msg = data.getData().getMsg();
        String question = msg.replace("@萨瓦迪卡", "");

        // 发送给AI
        webSocketClient.send(fromWxid, question);
    }
}
