package com.geektime.hmilydemo.common.account.mapper;

import com.geektime.hmilydemo.common.account.entity.AccountDTO;

public interface AccountMapper {

    int update(AccountDTO accountDTO);

    int confirm(AccountDTO accountDTO);

    int cancel(AccountDTO accountDTO);
}
