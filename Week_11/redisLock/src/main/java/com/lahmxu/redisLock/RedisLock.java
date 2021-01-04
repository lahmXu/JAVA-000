package com.lahmxu.redisLock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class RedisLock {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final long EXPIRE = 5 * 1000L;

    private static final long TIMEOUT = 10 * 1000L;

    private static final long WAITTIME = 1 * 1000L;


    /**
     * 分布式锁，setIfAbsent -> setnx
     *
     * @param key
     * @param value
     * @return
     */
    public boolean lock(String key, String value) {

        long requestTime = System.currentTimeMillis();
        while (true) {

            // 超时返回失败
            long waitTime = System.currentTimeMillis() - requestTime;
            if (waitTime > TIMEOUT) {
                log.info("redis加锁超时，key: {}, waitTime: {}", key, waitTime);
                return false;
            }

            // 使用setIfAbsent获取锁
            if (redisTemplate.opsForValue().setIfAbsent(key, String.valueOf(System.currentTimeMillis()) , EXPIRE, TimeUnit.MILLISECONDS)) {
                log.info("线程 {} 获取到锁",Thread.currentThread().getName());
                return true;
            }

            // 判断加锁时间是否超时
            String valueTime = (String) redisTemplate.opsForValue().get(key);
            if (!StringUtils.isEmpty(valueTime) && System.currentTimeMillis() - requestTime > TIMEOUT) {
                redisTemplate.opsForValue().getOperations().delete(key);
                log.info("redis单次加锁超时，key");
                return false;
            }

            try {
                log.info("线程 {} 等待 {} nanoSeconds key:{},value:{}",Thread.currentThread().getName(), WAITTIME, key, value);
                Thread.sleep(WAITTIME);
            } catch (InterruptedException e) {
                log.info("线程 {} 等待 {} nanoSeconds 异常 key:{},value:{}",Thread.currentThread().getName(), WAITTIME, key, value);
                e.printStackTrace();
            }
        }
    }

    /**
     * 解锁
     *
     * @param key
     */
    public void unlock(String key) {
        try {
            redisTemplate.opsForValue().getOperations().delete(key);
            log.info("线程 {} 解锁",Thread.currentThread().getName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
