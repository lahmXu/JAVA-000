package io.kimmking.dubbo.demo.api.service;

import com.geektime.hmilydemo.common.inventory.dto.InventoryDTO;

/**
 * 库存操作实体类
 */
public interface InventoryService {

    /**
     * 减库存
     *
     * @param inventoryDTO 库存实体DTO
     * @return boolean
     */
    Boolean decrease(InventoryDTO inventoryDTO);

    /**
     * mock 库存扣减try阶段异常.
     *
     * @param inventoryDTO dto
     * @return true boolean
     */
    Boolean mockWithTryException(InventoryDTO inventoryDTO);
}
