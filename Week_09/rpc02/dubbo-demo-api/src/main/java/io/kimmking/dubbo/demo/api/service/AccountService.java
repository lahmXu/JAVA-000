package io.kimmking.dubbo.demo.api.service;

import com.geektime.hmilydemo.common.account.entity.AccountDTO;

public interface AccountService {
    /**
     * 扣款支付.
     *
     * @param accountDTO 参数dto
     * @return true boolean
     */
    boolean payment(AccountDTO accountDTO);
}
