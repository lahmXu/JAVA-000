package com.lahmxu.xmq.core;

import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


public final class Xmq {

    private String topic;

    private int capacity;

    private volatile AtomicInteger writePointer = new AtomicInteger(0);

    private volatile AtomicInteger readPointer = new AtomicInteger(0);

    private List<XmqMessage> list;

    public Xmq(String topic, int capacity) {
        this.topic = topic;
        this.capacity = capacity;
        this.list = new LinkedList<>();
    }

    public synchronized boolean send(XmqMessage xmqMessage) {
        System.out.println("list add xmqMessage: "+ xmqMessage.toString());
        boolean result = list.add(xmqMessage);
        writePointer.incrementAndGet();
        return result;
    }

    public synchronized XmqMessage poll(long timeout) {
        // TODO 接口超时返回错误

        if (readPointer.get() >= writePointer.get()){
            System.out.println("no new message");
            return null;
        }

        XmqMessage result = list.get(readPointer.get());
        if (null == result){
            throw new RuntimeException("XmqMessage is null");
        }
        readPointer.incrementAndGet();
        return result;
    }
}
