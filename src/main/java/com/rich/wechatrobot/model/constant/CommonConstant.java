package com.rich.wechatrobot.model.constant;

/**
 * 公共静态变量
 *
 * @author AI
 * @date 2023/7/30 14:20
 **/

public class CommonConstant {

    // 群聊@我消息标识
    public static final String SAWADIKA = "@萨瓦迪卡";

    // 微信一次性发送最大字符设置，最大2048，这里设置小点
    public final static int WEIXIN_MAXLENGTH = 2000;
    // 等待AI回复消息锁
    public static final Object LOCK = new Object();
    // 待回复消息的微信ID
    public static String WAIT_RESPONSE_WEIXIN = null;
}
