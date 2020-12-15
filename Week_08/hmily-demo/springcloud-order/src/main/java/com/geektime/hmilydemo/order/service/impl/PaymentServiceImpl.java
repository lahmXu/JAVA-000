package com.geektime.hmilydemo.order.service.impl;

import com.geektime.hmilydemo.common.account.entity.AccountDTO;
import com.geektime.hmilydemo.common.inventory.dto.InventoryDTO;
import com.geektime.hmilydemo.common.order.entity.Order;
import com.geektime.hmilydemo.common.order.enums.OrderStatusEnum;
import com.geektime.hmilydemo.common.order.mapper.OrderMapper;
import com.geektime.hmilydemo.order.client.AccountClient;
import com.geektime.hmilydemo.order.client.InventoryClient;
import com.geektime.hmilydemo.order.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 支付操作实现类
 */
@Log4j2
@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    private final OrderMapper orderMapper;

    private final AccountClient accountClient;

    private final InventoryClient inventoryClient;

    @Autowired(required = false)
    public PaymentServiceImpl(final OrderMapper orderMapper, final AccountClient accountClient, InventoryClient inventoryClient) {
        this.orderMapper = orderMapper;
        this.accountClient = accountClient;
        this.inventoryClient = inventoryClient;
    }

    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    @Override
    public void makePayment(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAYING);
        accountClient.payment(buildAccountDTO(order));
        inventoryClient.decrease(buildInventoryDTO(order));
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public String mockPaymentInventoryWithTryException(Order order) {
        log.debug("===========执行springcloud  mockPaymentInventoryWithTryException 扣减资金接口==========");
        updateOrderStatus(order, OrderStatusEnum.PAYING);
        //扣除用户余额
        accountClient.payment(buildAccountDTO(order));
        inventoryClient.mockWithTryException(buildInventoryDTO(order));
        return "success";
    }

    /**
     * 更新订单状态-支付成功
     *
     * @param order 订单对象
     */
    public void confirmOrderStatus(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAY_SUCCESS);
        log.info("=========进行订单confirm操作完成================");
    }

    /**
     * 更新订单状态-支付失败
     *
     * @param order 订单对象
     */
    public void cancelOrderStatus(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAY_FAIL);
        log.info("=========进行订单cancel操作完成================");
    }

    /**
     * 构建账户实体
     *
     * @param order 订单实体
     * @return
     */
    private AccountDTO buildAccountDTO(Order order) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAmount(order.getTotalAmount());
        accountDTO.setUserId(order.getUserId());
        return accountDTO;
    }

    /**
     * 构建库存实体
     *
     * @param order 订单实体
     * @return 库存实例对象
     */
    private InventoryDTO buildInventoryDTO(Order order) {
        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setCount(order.getCount());
        inventoryDTO.setProductId(order.getProductId());
        return inventoryDTO;
    }

    /**
     * 更新订单状态
     *
     * @param order       订单实体
     * @param orderStatus 订单状态枚举类
     */
    private void updateOrderStatus(Order order, OrderStatusEnum orderStatus) {
        order.setStatus(orderStatus.getCode());
        orderMapper.update(order);
    }
}
