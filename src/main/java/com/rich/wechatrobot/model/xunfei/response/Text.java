package com.rich.wechatrobot.model.xunfei.response;

import lombok.Data;

@Data
public class Text {

    // 保留字段，可忽略
    private int question_tokens;

    // 包含历史问题的总tokens大小
    private int prompt_tokens;

    // 回答的tokens大小
    private int completion_tokens;

    // prompt_tokens和completion_tokens的和，也是本次交互计费的tokens大小
    private int total_tokens;

}
