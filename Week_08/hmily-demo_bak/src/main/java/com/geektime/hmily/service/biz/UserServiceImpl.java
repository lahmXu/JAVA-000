package com.geektime.hmily.service.biz;

import com.geektime.hmily.dao.UserMapper;
import com.geektime.hmily.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements IUserService{
    @Autowired
    UserMapper userMapper;

    @Override
    public List<User> list() {
        return userMapper.list();
    }

    @Override
    public void save(User user) {
        userMapper.save(user);
    }


}
