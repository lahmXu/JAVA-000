

package com.geektime.hmilydemo.common.account.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class AccountDTO implements Serializable {

    private static final long serialVersionUID = 7223470850578998427L;

    /**
     * 用户id
     */
    private long id;

    /**
     * 扣款金额
     */
    private String name;

}
