package com.rich.wechatrobot.model.xunfei.request;

import lombok.Data;

@Data
public class Text {

    // user表示是用户的问题，assistant表示AI的回复。取值为[user,assistant]
    private String role;

    // 用户和AI的对话内容。所有content的累计tokens需控制8192以内
    private String content;

}
