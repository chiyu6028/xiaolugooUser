package com.xiaolugoo.webapp.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * @Auther: ALEX
 * @Date: 2018/5/14 13:21
 * @Description:  支持跨越请求
 */
@ControllerAdvice(basePackages = "com.xiaolugoo.webapp.controller")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
  
    public JsonpAdvice() {  
  
        super("callback","jsonp");  
    }  
} 