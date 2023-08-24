package com.rich.wechatrobot.model.xunfei.request;

import lombok.Data;

@Data
public class AiRequest {

    private Header header;

    private Parameter parameter;

    private Payload payload;

}