package com.xiaolugoo.webapp.service;

import com.xiaolugoo.webapp.model.User;

public interface UserService {

    int insert(User record);

    User selectByPrimaryKey(Integer userId);

    User userLogin(String userAccount, String userPassword);
}
