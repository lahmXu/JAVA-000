package com.geektime.hmilydemo.common.account.mapper;

import com.geektime.hmilydemo.common.account.entity.AccountDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountMapper {

    int insert(AccountDTO accountDTO);

}
