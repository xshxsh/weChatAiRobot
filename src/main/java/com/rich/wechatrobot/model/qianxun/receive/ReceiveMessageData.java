package com.rich.wechatrobot.model.qianxun.receive;

import lombok.Data;
import lombok.ToString;

/**
 * 接收消息 内容
 */
@Data
@ToString
public class ReceiveMessageData {
    private String type;

    private String des;

    private ReceiveMessageDataData data;

    private String timestamp;

    private String wxid;

    private int port;

    private int pid;

    private String flag;

}
