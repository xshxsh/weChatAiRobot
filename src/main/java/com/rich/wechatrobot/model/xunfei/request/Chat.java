package com.rich.wechatrobot.model.xunfei.request;

import lombok.Data;

@Data
public class Chat {

    // 指定访问的领域。取值为 general
    private String domain;

    // 核采样阈值。用于决定结果随机性，取值越高随机性越强即相同的问题得到的不同答案的可能性越高。取值为[0,1],默认为0.5
    private double temperature;

    // 模型回答的tokens的最大长度。取值为[1,4096]，默认为2048
    private int max_tokens;

    // 从k个候选中随机选择⼀个（⾮等概率）。取值为[1，6],默认为4
    private int top_k;

    // 用于关联用户会话。需要保障用户下的唯一性
    private String chat_id;
}