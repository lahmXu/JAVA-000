package com.lahmxu.xmq.core;

/**
 * 生产者
 */
public class XmqProducer {

    private XmqBroker broker;

    public XmqProducer(XmqBroker broker) {
        this.broker = broker;
    }

    public boolean send(String topic, XmqMessage message) {
        Xmq xmq = this.broker.findXmq(topic);
        if (null == xmq) throw new RuntimeException("Topic[] doesn't exists.");
        return xmq.send(message);
    }
}
