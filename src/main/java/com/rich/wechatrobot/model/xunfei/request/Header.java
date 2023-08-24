package com.rich.wechatrobot.model.xunfei.request;

import lombok.Data;

@Data
public class Header {

    // 应用appid，从开放平台控制台创建的应用中获取
    private String app_id;

    // 每个用户的id，用于区分不同用户
    private String uid;

}