package io.kimmking.rpcfx.demo.provider;

import io.kimmking.rpcfx.demo.api.IUserService;
import io.kimmking.rpcfx.demo.api.User;
import org.springframework.stereotype.Service;

public class UserServiceImpl implements IUserService {

    @Override
    public User findById(Integer id) {
        return new User(id, "user--" + System.currentTimeMillis());
    }
}
