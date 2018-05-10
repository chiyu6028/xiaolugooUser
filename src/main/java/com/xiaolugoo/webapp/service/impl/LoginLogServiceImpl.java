package com.xiaolugoo.webapp.service.impl;

import com.xiaolugoo.webapp.mapper.LoginLogMapper;
import com.xiaolugoo.webapp.model.LoginLog;
import com.xiaolugoo.webapp.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Auther: ALEX
 * @Date: 2018/5/5 17:29
 * @Description:
 */
@Service
public class LoginLogServiceImpl implements LoginLogService {


    @Autowired
    LoginLogMapper loginLogMapper;


    @Override
    public int insert(LoginLog record) {
        return loginLogMapper.insert(record);
    }

    @Override
    public List<LoginLog> selectLoginLog(Map map) {
        return loginLogMapper.selectLoginLog(map);
    }
}
