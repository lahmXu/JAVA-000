package com.geektime.hmily.service.biz;

import com.geektime.hmily.pojo.req.SaveOrderReq;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("orderService")
public class OrderServiceImpl implements IOrderService {


    @Override
    @HmilyTCC(confirmMethod = "confirmTest",cancelMethod = "cancelTest")
    public void save(SaveOrderReq saveOrderReq) {

    }

    @Transactional(rollbackFor = Exception.class, timeout = 120)
    public void confirmTest(SaveOrderReq saveOrderReq){

    }

    @Transactional(rollbackFor = Exception.class,timeout = 120)
    public void cancelTest(SaveOrderReq saveOrderReq){

    }
}
