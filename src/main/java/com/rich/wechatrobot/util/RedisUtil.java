package com.rich.wechatrobot.util;

/**
 * redis 工具类
 *
 * @author AI
 * @date 2023/7/29 18:34
 **/

public class RedisUtil {

    /**
     * 获取redis key，ai请求内容存储到redis中
     */
    public static String getRequestKey() {
        return "aiRequest";
    }

    /**
     * 获取redis key，ai回复内容存储到redis中
     */
    public static String getResponseKey(String sid) {
        return "aiResponse:" + sid;
    }

}
