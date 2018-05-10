package com.xiaolugoo.webapp.config;

import com.xiaolugoo.webapp.filter.SessionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: ALEX
 * @Date: 2018/5/8 16:46
 * @Description:
 */

@Configuration
public class FilterConfig {


    @Bean
    public FilterRegistrationBean sessionFilte(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new SessionFilter());
        filterRegistrationBean.addUrlPatterns("/user/*");
        return filterRegistrationBean;
    }
}
