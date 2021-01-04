package com.lahmxu.redisPubSub.subscriber;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Subscriber implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] bytesBody = message.getBody();
        byte[] bytesChannel = message.getChannel();
        String patternStr = bytes != null ? new String(bytesChannel) : null;
        String msgBody = bytesBody != null ? new String(bytesBody) : null;
        String channel = bytesChannel != null ? new String(bytesChannel) : null;
        log.info("msgBody={}, channel={}, pattern={}", msgBody, channel, bytes);
        log.info("处理订餐操作：库存{}", msgBody);
    }
}
