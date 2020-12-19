package io.kimmking.dubbo.demo.provider.service;

import com.geektime.hmilydemo.common.order.entity.Order;
import com.geektime.hmilydemo.common.order.enums.OrderStatusEnum;
import com.geektime.hmilydemo.common.order.mapper.OrderMapper;
import io.kimmking.dubbo.demo.api.service.OrderService;
import io.kimmking.dubbo.demo.api.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Slf4j
@DubboService(version = "1.0.0")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
    private PaymentService paymentService;

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

    private com.geektime.hmilydemo.common.order.entity.Order buildOrder(Integer count, BigDecimal amount) {
        log.debug("构建订单对象");
        com.geektime.hmilydemo.common.order.entity.Order order = new Order();
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
