package com.xiaolugoo.webapp.service;


import com.xiaolugoo.webapp.model.LoginLog;

import java.util.List;
import java.util.Map;

public interface LoginLogService {

    int insert(LoginLog record);

    List<LoginLog> selectLoginLog(Map map);
}
