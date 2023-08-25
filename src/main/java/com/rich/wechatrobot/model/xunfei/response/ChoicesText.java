package com.rich.wechatrobot.model.xunfei.response;

import lombok.Data;

/**
 * @author AI
 * @email AI
 * @date 2023/7/27 20:22
 
 **/
@Data
public class ChoicesText {

    // AI的回答内容
    private String content;

    // 角色标识，固定为assistant，标识角色为AI
    private String role;

    // 结果序号，取值为[0,10]; 当前为保留字段，开发者可忽略
    private int index;

}
