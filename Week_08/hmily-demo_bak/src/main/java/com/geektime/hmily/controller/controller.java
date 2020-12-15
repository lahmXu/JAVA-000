package com.geektime.hmily.controller;

import com.geektime.hmily.pojo.entity.User;
import com.geektime.hmily.service.biz.IUserService;
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
    public void insert(@RequestBody User user){
        userService.save(user);
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public List<User> list(){
        return userService.list();
    }
}
