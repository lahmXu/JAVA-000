package com.geektime.multidatasource.service.biz;

import com.geektime.multidatasource.pojo.entity.User;

import java.util.List;

public interface IUserService {

    List<User> list();

    void save(User user);
}
