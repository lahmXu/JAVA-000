package com.lahmxu.xmq.core;

/**
 * 队列消费者
 *
 * @param <T>
 */
public class XmqConsumer<T> {

    private final XmqBroker broker;

    private Xmq xmq;

    public XmqConsumer(XmqBroker broker) {
        this.broker = broker;
    }

    public void subscribe(String topic) {
        this.xmq = this.broker.findXmq(topic);
        if (null == xmq) throw new RuntimeException("Topic [" + topic + "] does't exists.");
    }

    public XmqMessage<T> poll(long timeout) {
        return xmq.poll(timeout);
    }
}
