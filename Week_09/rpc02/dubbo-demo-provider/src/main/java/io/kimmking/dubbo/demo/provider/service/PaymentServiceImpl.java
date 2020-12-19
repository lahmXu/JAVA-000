package io.kimmking.dubbo.demo.provider.service;

import com.geektime.hmilydemo.common.account.entity.AccountDTO;
import com.geektime.hmilydemo.common.inventory.dto.InventoryDTO;
import com.geektime.hmilydemo.common.order.entity.Order;
import com.geektime.hmilydemo.common.order.enums.OrderStatusEnum;
import com.geektime.hmilydemo.common.order.mapper.OrderMapper;
import io.kimmking.dubbo.demo.api.service.AccountService;
import io.kimmking.dubbo.demo.api.service.InventoryService;
import io.kimmking.dubbo.demo.api.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@DubboService(version = "1.0.0")
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private OrderMapper orderMapper;

    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
    private AccountService accountService;

    @DubboReference(version = "1.0.0", url = "dubbo://127.0.0.1:12345")
    private InventoryService inventoryService;

    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    @Override
    public void makePayment(Order order) {
        updateOrderStatus(order, OrderStatusEnum.PAYING);
        accountService.payment(buildAccountDTO(order));
        inventoryService.decrease(buildInventoryDTO(order));
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmOrderStatus", cancelMethod = "cancelOrderStatus")
    public String mockPaymentInventoryWithTryException(Order order) {
        log.debug("===========执行 mockPaymentInventoryWithTryException 扣减资金接口==========");
        updateOrderStatus(order, OrderStatusEnum.PAYING);
        //扣除用户余额
        accountService.payment(buildAccountDTO(order));
        inventoryService.mockWithTryException(buildInventoryDTO(order));
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
