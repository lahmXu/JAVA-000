package com.lahmxu.redisPubSub;

import com.lahmxu.redisPubSub.subscriber.Subscriber;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import sun.rmi.runtime.Log;

@SpringBootApplication
@Slf4j
public class RedisPubSubApplication implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private RedisTemplate redisTemplate;

    private final static String channels ="news";

    public static void main(String[] args) {
        SpringApplication.run(RedisPubSubApplication.class, args);
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        ConfigurableApplicationContext applicationContext = applicationReadyEvent.getApplicationContext();
        Thread subThread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Subscriber subscriber = applicationContext.getBean(Subscriber.class);
                LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
                lettuceConnectionFactory.getConnection().subscribe(subscriber,channels.getBytes());
            }
        });

        Thread pubThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Subscriber subscriber = applicationContext.getBean(Subscriber.class);
                LettuceConnectionFactory lettuceConnectionFactory = (LettuceConnectionFactory) redisTemplate.getConnectionFactory();
                long publishNum = lettuceConnectionFactory.getConnection().publish(channels.getBytes(),String.valueOf(-1).getBytes());
                log.info("已发布消息个数: {}",publishNum);
                lettuceConnectionFactory.getConnection().close();
            }
        });
        subThread1.start();
        subThread1.join();
        pubThread.start();
        pubThread.join();

        System.exit(-1);
    }
}
