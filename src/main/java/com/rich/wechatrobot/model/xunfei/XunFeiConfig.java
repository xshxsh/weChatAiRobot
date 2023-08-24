package com.rich.wechatrobot.model.xunfei;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 讯飞配置
 *
 * @author AI
 * @date 2023/7/29 13:16
 **/

@Component
@Data
@ConfigurationProperties(prefix = "xunfei")
public class XunFeiConfig {

    private String hostUrl;

    private String appid;

    private String apisecret;

    private String apikey;

}
