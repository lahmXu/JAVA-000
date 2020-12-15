package com.geektime.hmilydemo.order.service;

import java.math.BigDecimal;

/**
 * 订单业务接口
 */
public interface OrderService {

    /**
     * 创建订单并且进行扣除账户余额支付，并进行库存扣减操作.
     *
     * @param count  购买数量
     * @param amount 支付金额
     * @return string string
     */
    String orderPay(Integer count, BigDecimal amount);

    /**
     * 模拟在订单支付操作中，库存在try阶段中的库存异常.
     *
     * @param count  购买数量
     * @param amount 支付金额
     * @return string string
     */
    String mockInventoryWithTryException(Integer count, BigDecimal amount);
}
