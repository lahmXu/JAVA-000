package io.kimmking.dubbo.demo.provider.service;

import com.geektime.hmilydemo.common.inventory.dto.InventoryDTO;
import com.geektime.hmilydemo.common.inventory.mapper.InventoryMapper;
import io.kimmking.dubbo.demo.api.service.InventoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@DubboService(version = "1.0.0")
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryMapper inventoryMapper;

    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    @Override
    public Boolean decrease(InventoryDTO inventoryDTO) {
        log.info("==========执行try减库存方法===========");
        inventoryMapper.decrease(inventoryDTO);
        return true;
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmMethod", cancelMethod = "cancelMethod")
    @Transactional
    public Boolean mockWithTryException(InventoryDTO inventoryDTO) {
        throw new HmilyRuntimeException("库存扣减异常！");
    }

    public Boolean confirmMethod(InventoryDTO inventoryDTO) {
        log.info("==========confirmMethod库存确认方法===========");
        return inventoryMapper.confirm(inventoryDTO) > 0;
    }

    public Boolean cancelMethod(InventoryDTO inventoryDTO) {
        log.info("==========cancelMethod库存取消方法===========");
        return inventoryMapper.cancel(inventoryDTO) > 0;
    }
}
