package io.kimmking.dubbo.demo.provider.service;

import com.geektime.hmilydemo.common.account.entity.AccountDTO;
import com.geektime.hmilydemo.common.account.mapper.AccountMapper;
import io.kimmking.dubbo.demo.api.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
@DubboService(version = "1.0.0")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    @HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean payment(AccountDTO accountDTO) {
        log.info("============执行try付款接口===============");
        accountMapper.update(accountDTO);
        return true;
    }

    public boolean confirm(final AccountDTO accountDTO) {
        log.info("============执行confirm 付款接口===============");
        return accountMapper.confirm(accountDTO) > 0;
    }

    public boolean cancel(final AccountDTO accountDTO) {
        log.info("============执行cancel 付款接口===============");
        return accountMapper.cancel(accountDTO) > 0;
    }
}
