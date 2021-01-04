package com.geektime.hmilydemo.common.amountRMB.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 美元账户余额表
 */
@Data
public class DollarAmountDO implements Serializable {

    private static final long serialVersionUID = -6860470593983446844L;

    private long id;

    private long accountId;

    private BigDecimal amount;

}
