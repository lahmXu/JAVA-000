package com.geektime.hmilydemo.order.controller;

import com.geektime.hmilydemo.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * 订单操作控制类
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * 订单支付接口（注意这里模拟的是创建订单并进行支付扣减库存等操作）
     *
     * @param count  数量
     * @param amount 金额
     * @return 订单支付结果
     */
    @PostMapping(value = "/orderPay")
    public String orderPay(@RequestParam(value = "count") Integer count,
                           @RequestParam(value = "amount") BigDecimal amount) {
        return orderService.orderPay(count, amount);
    }

    @PostMapping(value = "/mockInventoryWithTryException")
    public String mockInventoryWithTryException(@RequestParam(value = "count") Integer count,
                                                @RequestParam(value = "amount") BigDecimal amount) {
        return orderService.mockInventoryWithTryException(count, amount);
    }
}
