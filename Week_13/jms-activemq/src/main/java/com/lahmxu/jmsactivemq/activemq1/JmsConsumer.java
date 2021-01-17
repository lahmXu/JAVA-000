package com.lahmxu.jmsactivemq.activemq1;

        import org.springframework.jms.annotation.JmsListener;
        import org.springframework.stereotype.Component;

        import java.util.Map;

@Component
public class JmsConsumer {

    @JmsListener(destination = "lahmxu111")
    public void receiveMessage(final Map message) {
        System.out.println("receive message: " + message.toString());
    }
}
