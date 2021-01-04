package com.lahmxu.redisLock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class RedisLockApplication implements ApplicationRunner {

	private final static String LOCK = "redis_lock";

	private volatile int amount = 10;

	@Autowired
	private RedisLock redisLock;

	@Autowired
	private StockBiz stockBiz;

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(RedisLockApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Thread thread1 = new Thread(new Runnable() {
			@Override
			public void run() {
				if (redisLock.lock(LOCK,String.valueOf(System.currentTimeMillis()))){
					amount = stockBiz.decrement(amount);
					redisLock.unlock(LOCK);
				}

			}
		},"thread-1");
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				if (redisLock.lock(LOCK,String.valueOf(System.currentTimeMillis()))){
					amount = stockBiz.decrement(amount);
					redisLock.unlock(LOCK);
				}
			}
		},"thread-2");
		thread1.start();
		thread2.start();
		thread1.join();
		thread2.join();

		System.exit(-1);
	}
}
