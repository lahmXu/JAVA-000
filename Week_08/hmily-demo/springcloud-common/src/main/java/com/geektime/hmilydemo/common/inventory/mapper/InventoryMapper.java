package com.geektime.hmilydemo.common.inventory.mapper;

import com.geektime.hmilydemo.common.inventory.dto.InventoryDTO;

public interface InventoryMapper {

    /**
     * 减库存
     * @param inventoryDTO 库存实体DTO
     * @return
     */
    int decrease(InventoryDTO inventoryDTO);

    /**
     * 确认减库存
     * @param inventoryDTO 库存实体DTO
     * @return
     */
    int confirm(InventoryDTO inventoryDTO);

    /**
     * 取消减库存
     * @param inventoryDTO 库存实体DTO
     * @return
     */
    int cancel(InventoryDTO inventoryDTO);
}
