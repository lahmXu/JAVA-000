package io.kimmking.dubbo.demo.api.service;

import com.geektime.hmilydemo.common.order.entity.Order;

/**
 * 支付操作接口
 */
public interface PaymentService {

    /**
     * 订单支付
     *
     * @param order 订单实体
     */
    void makePayment(Order order);

    /**
     * mock订单支付的时候库存异常.
     *
     * @param order 订单实体
     * @return String string
     */
    String mockPaymentInventoryWithTryException(Order order);
}
