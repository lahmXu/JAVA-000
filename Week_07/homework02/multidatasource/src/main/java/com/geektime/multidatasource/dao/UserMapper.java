package com.geektime.multidatasource.dao;

import com.geektime.multidatasource.pojo.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    List<User> list();

    void save(@Param("user") User user);
}
