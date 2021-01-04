package com.lahmxu.redisLock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StockBiz {

    public int decrement(int amount){
        amount -= 1;
        log.info("库存减一，操作线程：{}，剩余：{}",Thread.currentThread().getName(),amount);
        return  amount;
    }
}
