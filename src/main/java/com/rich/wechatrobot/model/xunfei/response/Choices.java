package com.rich.wechatrobot.model.xunfei.response;

import lombok.Data;

import java.util.List;

@Data
public class Choices {
    // 文本响应状态，取值为[0,1,2]; 0代表首个文本结果；1代表中间文本结果；2代表最后一个文本结果
    private int status;

    // 返回的数据序号，取值为[0,9999999]
    private int seq;

    //
    private List<ChoicesText> text;
}