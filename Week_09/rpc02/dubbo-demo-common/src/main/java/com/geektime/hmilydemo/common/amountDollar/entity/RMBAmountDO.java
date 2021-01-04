package com.geektime.hmilydemo.common.amountDollar.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 人民币余额表
 */
@Data
public class RMBAmountDO implements Serializable {

    private static final long serialVersionUID = 2380400639014077182L;

    private long id;

    private long accountId;

    private BigDecimal amount;
}
