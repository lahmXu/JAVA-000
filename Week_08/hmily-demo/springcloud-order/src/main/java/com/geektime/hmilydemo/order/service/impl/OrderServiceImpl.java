package com.geektime.hmilydemo.order.service.impl;

import com.geektime.hmilydemo.common.order.entity.Order;
import com.geektime.hmilydemo.common.order.enums.OrderStatusEnum;
import com.geektime.hmilydemo.common.order.mapper.OrderMapper;
import com.geektime.hmilydemo.order.service.OrderService;
import com.geektime.hmilydemo.order.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * 订单业务实现类
 */
@Log4j2
@Service("orderService")
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final PaymentService paymentService;

    @Autowired(required = false)
    private OrderServiceImpl(final OrderMapper orderMapper, final PaymentService paymentService) {
        this.orderMapper = orderMapper;
        this.paymentService = paymentService;
    }

    @Override
    public String orderPay(Integer count, BigDecimal amount) {
        log.info("=============发起订单支付=============");
        Order order = saveOrder(count, amount);
        paymentService.makePayment(order);
        return "success";
    }

    @Override
    public String mockInventoryWithTryException(Integer count, BigDecimal amount) {
        log.info("=============发起订单支付=============");
        Order order = saveOrder(count, amount);
        return paymentService.mockPaymentInventoryWithTryException(order);
    }

    private Order saveOrder(Integer count, BigDecimal amount) {
        final Order order = buildOrder(count, amount);
        orderMapper.save(order);
        return order;
    }

    private Order buildOrder(Integer count, BigDecimal amount) {
        log.debug("构建订单对象");
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setNumber(UUID.randomUUID().toString());
        //demo中的表里只有商品id为 1的数据
        order.setProductId("1");
        order.setStatus(OrderStatusEnum.NOT_PAY.getCode());
        order.setTotalAmount(amount);
        order.setCount(count);
        order.setUserId("10000");
        return order;
    }
}
