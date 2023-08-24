package com.rich.wechatrobot.service.xunfei;

import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.rich.wechatrobot.config.exception.BizException;
import com.rich.wechatrobot.model.qianxun.QianXunBaseRequest;
import com.rich.wechatrobot.model.qianxun.SendTextMsgRequest;
import com.rich.wechatrobot.model.xunfei.XunFeiConfig;
import com.rich.wechatrobot.model.xunfei.request.*;
import com.rich.wechatrobot.model.xunfei.response.AiResponse;
import com.rich.wechatrobot.model.xunfei.response.ChoicesText;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.rich.wechatrobot.model.constant.CommonConstant.WEIXIN_MAXLENGTH;
import static com.rich.wechatrobot.model.constant.QianXunUrl.QX_PORT;

/**
 * websocket client
 *
 * @author AI
 * @date 2023/7/29 14:16
 **/
@Slf4j
@Service
@ClientEndpoint
public class WebSocketClient {

    // websocket session 容器
    private static final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    // 存储问题来源(谁提问的：<sessionId,fromWxid>)
    private static final ConcurrentHashMap<String, String> SESSION_MAP = new ConcurrentHashMap<>();
    // 存储答案(<sessionId,list<answer>>)
    private static final ConcurrentHashMap<String, List<String>> ANSWER_MAP = new ConcurrentHashMap<>();
    @Autowired
    XunFeiConfig xunFeiConfig;

    /**
     * 接收消息
     */
    @OnMessage
    public void onMessage(String response, Session session) {
//        System.out.println("接收的sessionID：" + session.getId());
        handlerMessageResponse(session, response);
    }

    /**
     * 异常处理
     */
    @OnError
    public void onError(Throwable throwable) {
        BizException.throwIf("讯飞AI通讯异常", throwable);
    }

    /**
     * 回复微信
     */
    private void sendWeixin(String fromWxid, String message) {
        QianXunBaseRequest<SendTextMsgRequest> qianXunBaseRequest = new QianXunBaseRequest();
        qianXunBaseRequest.setType("Q0001");
        SendTextMsgRequest sendTextMsgRequest = new SendTextMsgRequest();
        sendTextMsgRequest.setWxid(fromWxid);
        sendTextMsgRequest.setMsg(message);
        qianXunBaseRequest.setData(sendTextMsgRequest);
        try (HttpResponse httpResponse = HttpUtil.createPost(QX_PORT).body(JSONUtil.toJsonStr(qianXunBaseRequest)).execute()) {
            BizException.throwIf(httpResponse == null || !httpResponse.isOk(), "AI发送微信消息响应异常");
        }
    }


    /**
     * 微信每次发送不超过2048个汉字
     */
    public List<String> splitString(String input) {
        if (input == null || input.length() <= WEIXIN_MAXLENGTH) {
            return Collections.singletonList(input);
        }

        List<String> result = new ArrayList<>();
        int length = input.length();
        int startIndex = 0;

        while (startIndex < length) {
            int endIndex = Math.min(startIndex + WEIXIN_MAXLENGTH, length);
            String substring = input.substring(startIndex, endIndex);
            result.add(substring);
            startIndex += substring.length();
        }

        return result;
    }

    /**
     * 处理接收到的消息
     */
    private void handlerMessageResponse(Session session, String response) {
        String sessionId = session.getId();
        String fromWxid = SESSION_MAP.get(sessionId);
        List<String> answerList = ANSWER_MAP.get(sessionId);
        if (CollectionUtils.isEmpty(answerList)) {
            answerList = new ArrayList<>(8);
        }

        AiResponse aiResponse = JSONUtil.toBean(response, AiResponse.class);
        int status = aiResponse.getHeader().getStatus();
        List<ChoicesText> textList = aiResponse.getPayload().getChoices().getText();
        for (ChoicesText text : textList) {
            // 收到消息就存起来
            answerList.add(text.getContent());
            ANSWER_MAP.put(sessionId, answerList);
            // 收到最后一条消息
            if (status == 2) {
                StringBuilder stringBuilder = new StringBuilder();
                for (Object answer : answerList) {
                    stringBuilder.append(answer);
                }
                String allAnswer = stringBuilder.toString().replace("\n\n", "\n");
                log.info("AI回复：{}", allAnswer);

                // 微信每次发送不超过2048个汉字
                List<String> finaleAnswerList = splitString(allAnswer);
                for (String answer : finaleAnswerList) {
                    sendWeixin(fromWxid, answer);
                }

                // 删除已回复的答案
                ANSWER_MAP.remove(sessionId);
                SESSION_MAP.remove(sessionId);
            }
        }
    }

    /**
     * 发送消息给AI
     */
    public void send(String fromWxid, String question) {
        Session session = initSession();
        SESSION_MAP.put(session.getId(), fromWxid);
//        System.out.println("发送的sessionID：" + session.getId());
        String request = buildRequest(question);
        session.getAsyncRemote().sendText(request);
        log.info("发送消息给AI：{}", question);
        try {
            if (session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            BizException.throwIf("关闭讯飞AIsession异常", e);
        }
    }

    /**
     * 发送消息给AI
     */
    public void send(String question) {
        Session session = initSession();
//        System.out.println("发送的sessionID：" + session.getId());
        String request = buildRequest(question);
        session.getAsyncRemote().sendText(request);
        log.info("发送消息给AI：{}", question);
        try {
            if (session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            BizException.throwIf("关闭讯飞AIsession异常", e);
        }
    }

    /**
     * 初始化session
     */
    private Session initSession() {
        try {
            String authorizationUrl = getAuthorizationUrl();
            String url = authorizationUrl.replace("https://", "wss://").replace("http://", "ws://");
            Session session = container.connectToServer(WebSocketClient.class, URI.create(url));
            return session;
        } catch (Exception e) {
            BizException.throwIf("连接讯飞AIwebsocket异常", e);
        }
        return null;
    }

    /**
     * 获取带token得请求地址
     */
    private String getAuthorizationUrl() throws Exception {
        //获取host
        URL url = new URL(xunFeiConfig.getHostUrl());
        //获取鉴权时间 date
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
//        System.out.println("format:\n" + format );
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        //获取signature_origin字段
        StringBuilder builder = new StringBuilder("host: ").append(url.getHost()).append("\n").append("date: ").append(date).append("\n").append("GET ").append(url.getPath()).append(" HTTP/1.1");
//        System.out.println("signature_origin:\n" + builder);
        //获得signatue
        Charset charset = StandardCharsets.UTF_8;
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec sp = new SecretKeySpec(xunFeiConfig.getApisecret().getBytes(charset), "hmacsha256");
        mac.init(sp);
        byte[] basebefore = mac.doFinal(builder.toString().getBytes(charset));
        String signature = Base64.getEncoder().encodeToString(basebefore);
        //获得 authorization_origin
        String authorization_origin = String.format("api_key=\"%s\",algorithm=\"%s\",headers=\"%s\",signature=\"%s\"", xunFeiConfig.getApikey(), "hmac-sha256", "host date request-line", signature);
        //获得authorization
        String authorization = Base64.getEncoder().encodeToString(authorization_origin.getBytes(charset));
        //获取httpurl
        HttpUrl httpUrl = HttpUrl.parse("https://" + url.getHost() + url.getPath()).newBuilder().//
                addQueryParameter("authorization", authorization).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        String urlStr = httpUrl.toString();
//        System.out.println(urlStr);
        return urlStr;
    }

    /**
     * 构建AI请求参数
     */
    private String buildRequest(String question) {
        //填充header
        Header header = new Header();
        header.setApp_id(xunFeiConfig.getAppid());
//        header.setUid();

        //填充parameter.chat
        Chat chat = new Chat();
        chat.setDomain("general");
        chat.setTemperature(0.5d);
        chat.setMax_tokens(4096);
        chat.setTop_k(4);
        chat.setChat_id("123456789");

        Parameter parameter = new Parameter();
        parameter.setChat(chat);

        //填充payload.message.text
        Text text = new Text();
        text.setRole("user");
        text.setContent(question);
        Message message = new Message();
        ArrayList<Text> list = new ArrayList<>();
        list.add(text);
        message.setText(list);
        Payload payload = new Payload();
        payload.setMessage(message);

        AiRequest aiRequest = new AiRequest();
        aiRequest.setHeader(header);
        aiRequest.setParameter(parameter);
        aiRequest.setPayload(payload);

        String aiRequestStr = JSONUtil.toJsonStr(aiRequest);
        return aiRequestStr;
    }

}