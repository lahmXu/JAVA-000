package com.geektime.hmilydemo.inventory.service.impl;

import com.geektime.hmilydemo.common.inventory.dto.InventoryDTO;
import com.geektime.hmilydemo.common.inventory.mapper.InventoryMapper;
import com.geektime.hmilydemo.inventory.service.InventoryService;
import lombok.extern.log4j.Log4j2;
import org.dromara.hmily.annotation.HmilyTCC;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 库存操作实现类
 */
@Log4j2
@Service("inventoryService")
public class InventoryServiceImpl implements InventoryService {

    private final InventoryMapper inventoryMapper;

    @Autowired(required = false)
    public InventoryServiceImpl(InventoryMapper inventoryMapper) {
        this.inventoryMapper = inventoryMapper;
    }

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
