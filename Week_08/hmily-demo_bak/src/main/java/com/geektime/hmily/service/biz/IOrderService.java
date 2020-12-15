package com.geektime.hmily.service.biz;

import com.geektime.hmily.pojo.req.SaveOrderReq;
import org.dromara.hmily.annotation.Hmily;

public interface IOrderService {

    @Hmily
    void save(SaveOrderReq saveOrderReq);
}
