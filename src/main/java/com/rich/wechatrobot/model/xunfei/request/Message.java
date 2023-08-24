package com.rich.wechatrobot.model.xunfei.request;

import lombok.Data;

import java.util.List;

@Data
public class Message {
    private List<Text> text;
}