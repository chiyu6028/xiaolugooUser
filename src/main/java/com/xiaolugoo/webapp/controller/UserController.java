package com.xiaolugoo.webapp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaolugoo.webapp.model.LoginLog;
import com.xiaolugoo.webapp.model.User;
import com.xiaolugoo.webapp.service.LoginLogService;
import com.xiaolugoo.webapp.service.UserService;
import com.xiaolugoo.webapp.util.ResultData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "用户")
@Controller
@RequestMapping(value = "/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    ObjectMapper mapper = new ObjectMapper();

    @Autowired
    UserService userService;

    @Autowired
    LoginLogService loginLogService;

    @Value("validateCode")
    String validateCodeType;

    @ApiOperation(value = "创建用户",notes = "根据User创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", dataType = "User")
    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
    public String userInsert(User user) throws JsonProcessingException {
        logger.debug("创建用户");
        ResultData result = new ResultData();
        int res = 0;
        try {
            res = userService.insert(user);
            List resList = new ArrayList();
            resList.add(res);
            if (res > 0){
                result.setFlag("1");
                result.setMsg("新增成功！");
                result.setResult(resList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag("0");
            result.setMsg("新增失败！");
        }

        return mapper.writeValueAsString(result);
    }

    @ApiOperation(value = "修改用户",notes = "根据User修改用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", dataType = "User")
    @ResponseBody
    @RequestMapping(value = "/update", method = {RequestMethod.GET,RequestMethod.POST})
    public String userUpdate(User user,HttpServletRequest request) throws JsonProcessingException {
        logger.debug("修改用户");
        ResultData result = new ResultData();
        HttpSession session = request.getSession();
        int res = 0;
        try {
            res = userService.updateByPrimaryKeySelective(user);
            List resList = new ArrayList();
            resList.add(res);
            if (res > 0){
                //修改后，更新session信息
                User newUser = userService.selectByPrimaryKey(user.getUserId());
                if (newUser != null){
                    setSessionInfo(session,newUser);
                }
                result.setFlag("1");
                result.setMsg("修改成功！");
                result.setResult(resList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag("0");
            result.setMsg("新增失败！");
        }

        return mapper.writeValueAsString(result);
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Integer")
    @ResponseBody
    @RequestMapping(value = "/getUser/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public String selectByPrimaryKey(@PathVariable("id") int id) throws JsonProcessingException {
        logger.debug("用户详情查询");
        ResultData result = new ResultData();
        try {
            User user = userService.selectByPrimaryKey(id);
            List list = new ArrayList();
            list.add(user);
            if (user != null){
                result.setFlag("1");
                result.setMsg("查询成功！");
                result.setResult(list);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setFlag("0");
            result.setMsg("新增失败！");
        }
        return mapper.writeValueAsString(result);
    }

    @ApiOperation(value = "用户登陆",notes = "根据账户密码登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAccount",value = "账户",required = true,paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userPassword",value = "密码",required = true,paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "validateCode",value = "验证码",paramType = "query", dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
    public String userLogin(
            @PathParam("userAccount") String userAccount,
            @PathParam("userPassword") String userPassword,
            @PathParam("validateCode") String validateCode,
            HttpServletRequest request) throws JsonProcessingException {
        logger.debug("用户登陆");
        HttpSession session = request.getSession();
        ResultData result = null;

        if(validateCodeType.equals("true") && ! validateCode.equals(session.getAttribute("validateCode"))){
            result.setFlag("0");
            result.setMsg("验证码不正确！");
            return mapper.writeValueAsString(result);
        }

        try {
            User user = userService.userLogin(userAccount, userPassword);
            LoginLog login = new LoginLog();
            if (user != null ){

                //设置登陆标识符
                session.setAttribute("loginFlag",true);

                //登陆信息存到sesseion中
                setSessionInfo(session, user);

                //登陆日志
                login.setLoginIp(getClinetIp(request));
                login.setUserId(user.getUserId());
                login.setLoginStatus("1");
                loginLogService.insert(login);

                List list = new ArrayList();
                list.add(user);
                result = new ResultData("1","登陆成功！",list);
                /*result.setFlag("1");
                result.setMsg("登陆成功！");
                result.setResult(list);*/
            }else{
                result = new ResultData("0","用户名或密码不正确！");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
            /*result.setFlag("0");
            result.setMsg("登陆失败！");*/
            result = new ResultData("0","登陆失败！");
        }
        return mapper.writeValueAsString(result);
    }

    @ApiOperation(value = "用户退出",notes = "用户退出登陆，跳转登陆页")
    @ResponseBody
    @RequestMapping(value = "/logout", method = {RequestMethod.GET,RequestMethod.POST})
    public String userLogout(HttpServletRequest request) throws JsonProcessingException {
        logger.debug("用户退出");
        HttpSession session = request.getSession();
        session.invalidate();
        ResultData result = new ResultData("-100","session会话过期，请重新登陆");
        return mapper.writeValueAsString(result);
    }


    /*
    * 将用户信息存储到session中
    * */
    public void setSessionInfo(HttpSession session, User user){

        Class userClass = User.class;
        Field[] fields = userClass.getDeclaredFields();
        for (Field field : fields){
            String[] strField = field.toString().split("\\.");
            String fiedlName = strField[strField.length-1];
            String methodName = "get" + fiedlName.substring(0,1).toUpperCase() + fiedlName.substring(1);
            try {
                Method method = userClass.getDeclaredMethod(methodName);
                Object res = method.invoke(user);
                if (res != null && !fiedlName.equals("userPassword")){
                    session.setAttribute(fiedlName,res);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * 获取客户端ip地址
    * */
    public String getClinetIp(HttpServletRequest request){
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
