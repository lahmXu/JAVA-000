package com.geektime.hmily.pojo.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SaveOrderReq {

    private Long userId;

    private BigDecimal amount;

    private Long goodId;

}
