package com.xiaolugoo.webapp.controller;

import com.xiaolugoo.webapp.model.User;
import com.xiaolugoo.webapp.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

@Api(tags = "登陆")
@Controller
@RequestMapping(value = "/user")
public class LoginController {

    @Autowired
    LoginService loginService;

    @ApiOperation(value = "创建用户",notes = "根据User创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @ResponseBody
    @RequestMapping(value = "/add", produces = {"application/json;charset=UTF-8"})
    public int userInsert(User user, HttpServletRequest request){
        HttpSession session = request.getSession();
        return loginService.insert(user);
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Integer")
    @ResponseBody
    @RequestMapping(value = "/getUser/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public User selectByPrimaryKey(@PathVariable("id") int id){
        return loginService.selectByPrimaryKey(id);
    }
}
