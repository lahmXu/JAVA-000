package com.geektime.hmily.service.biz;

import com.geektime.hmily.pojo.entity.User;

import java.util.List;

public interface IUserService {

    List<User> list();

    void save(User user);
}
