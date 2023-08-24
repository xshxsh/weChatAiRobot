package com.rich.wechatrobot.model.qianxun;

import lombok.Data;

@Data
public class SendTextMsgRequest {

    /**
     * 1.消息内支持文本代码，详情见文本代码章节
     * 2.微信最多支持4096个字符，相当于2048个汉字，请勿超出否则崩溃
     */
    private String msg;

    /**
     * 要接受消息的微信ID
     */
    private String wxid;
}