package com.xiaolugoo.webapp.service;

import com.xiaolugoo.webapp.model.User;

public interface LoginService {

    int insert(User record);

    User selectByPrimaryKey(Integer userId);
}
