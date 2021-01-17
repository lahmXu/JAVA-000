package com.lahmxu.jmsactivemq;

import com.lahmxu.jmsactivemq.activemq1.JmsProducer;
import oracle.jvm.hotspot.jfr.Producer;
import org.apache.activemq.broker.BrokerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableJms
public class JmsActivemqApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(JmsActivemqApplication.class, args);
    }

    @Autowired
    private JmsProducer jmsProducer;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        String topic = "lahmxu111";
        Map<String, String> message = new HashMap<>(1);
        message.put("lahmxu", "hello");
        System.out.println("----------------------");
        jmsProducer.sendMessage(topic, message);
        System.out.println("send message:" + message.toString());
    }
}
