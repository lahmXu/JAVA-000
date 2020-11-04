package com.lahmxu.gateway;

import com.lahmxu.gateway.inbound.HttpInboundServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

@SpringBootApplication
public class GatewayApplication implements ApplicationListener<ApplicationReadyEvent> {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        try {
            new HttpInboundServer(8081,"http://127.0.0.1:8082/backend").run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
