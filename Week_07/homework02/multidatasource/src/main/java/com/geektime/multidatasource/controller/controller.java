package com.geektime.multidatasource.controller;

import com.geektime.multidatasource.pojo.entity.User;
import com.geektime.multidatasource.service.biz.IUserService;
import com.geektime.multidatasource.service.dynamicDataSource.DataSourceType;
import com.geektime.multidatasource.service.dynamicDataSource.UseDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class controller {

    @Autowired
    IUserService userService;

    @RequestMapping(value = "/insert",method = RequestMethod.POST)
    @UseDataSource(value = DataSourceType.MASTER)
    public void insert(@RequestBody User user){
        userService.save(user);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @UseDataSource(value = DataSourceType.SLAVE)
    public List<User> list(){
        return userService.list();
    }
}
