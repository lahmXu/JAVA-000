package com.lahmxu.xmq.core;

import lombok.SneakyThrows;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;


public final class Xmq {

    private String topic;

    private int capacity;

    // 此处修改消息队列是基于内存queue还是数组，亦或者是存储，对其他操作不产生影响
    private LinkedBlockingQueue<XmqMessage> queue;

    public Xmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.queue = new LinkedBlockingQueue(capacity);
    }

    public boolean send(XmqMessage xmqMessage) {
        return queue.offer(xmqMessage);
    }

    public XmqMessage poll() {
        return queue.poll();
    }

    @SneakyThrows
    public XmqMessage poll(long timeout) {
        return queue.poll(timeout, TimeUnit.MILLISECONDS);
    }
}
