package com.xiaolugoo.webapp.service.impl;

import com.xiaolugoo.webapp.mapper.UserMapper;
import com.xiaolugoo.webapp.model.User;
import com.xiaolugoo.webapp.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

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
}
