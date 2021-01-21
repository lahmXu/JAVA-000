package com.lahmxu.xmq.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 队列中间
 */
public final class XmqBroker {

    public static final int CAPACITY = 1000;

    private final Map<String, Xmq> xmqMap = new ConcurrentHashMap<>(64);

    public void createTopic(String name) {
        xmqMap.putIfAbsent(name, new Xmq(name, CAPACITY));
    }

    public Xmq findXmq(String topic) {
        return this.xmqMap.get(topic);
    }

    public XmqProducer createProducer() {
        return new XmqProducer(this);
    }

    public XmqConsumer createConsumer() {
        return new XmqConsumer(this);
    }
}
