package com.geektime.hmilydemo.common.inventory.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 数据传输对象
 */
@Data
public class InventoryDTO implements Serializable {

    private static final long serialVersionUID = 8229355519336565493L;

    /**
     * 商品id.
     */
    private String productId;

    /**
     * 数量.
     */
    private Integer count;
}
