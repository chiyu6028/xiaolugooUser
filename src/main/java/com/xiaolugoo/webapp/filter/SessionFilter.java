package com.xiaolugoo.webapp.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaolugoo.webapp.util.ResultData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther: ALEX
 * @Date: 2018/5/8 14:21
 * @Description:
 */
public class SessionFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(SessionFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.debug("sessionFilter init");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        ObjectMapper mapper = new ObjectMapper();
        String uri = httpServletRequest.getRequestURI();
        boolean login = "/user/login".equals(uri);
        if ((session.getAttribute("loginFlag") != null && (boolean)session.getAttribute("loginFlag"))||login){
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            ResultData resultData = new ResultData();
            servletResponse.setCharacterEncoding("utf-8");
            servletResponse.setContentType("text/json;charset=UTF-8");
            try (PrintWriter o = servletResponse.getWriter()) {
                resultData.setFlag("-100");
                resultData.setMsg("session会话过期，请重新登陆");
                String msg = mapper.writeValueAsString(resultData);
                o.print(msg);
                logger.error("session会话过期");
                o.flush();
            }
            return;
        }


    }

    @Override
    public void destroy() {
        logger.debug("sessionFilter destroy");
    }
}
