package com.xiaolugoo.webapp.service.impl;

import com.xiaolugoo.webapp.mapper.UserMapper;
import com.xiaolugoo.webapp.model.User;
import com.xiaolugoo.webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public User selectByPrimaryKey(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    @Override
    public User userLogin(String userAccount, String userPassword) {
        Map map = new HashMap();
        map.put("userAccount",userAccount);
        map.put("userPassword",userPassword);
        return  userMapper.userLogin(map);
    }
}
