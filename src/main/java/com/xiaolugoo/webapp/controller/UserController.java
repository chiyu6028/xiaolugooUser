package com.xiaolugoo.webapp.controller;

import com.xiaolugoo.webapp.model.User;
import com.xiaolugoo.webapp.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
import java.lang.reflect.Field;

@Api(tags = "用户")
@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation(value = "创建用户",notes = "根据User创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @ResponseBody
    @RequestMapping(value = "/add", method = {RequestMethod.GET,RequestMethod.POST})
    public int userInsert(User user, HttpServletRequest request){
        HttpSession session = request.getSession();
        return userService.insert(user);
    }

    @ApiOperation(value="获取用户详细信息", notes="根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Integer")
    @ResponseBody
    @RequestMapping(value = "/getUser/{id}",method = {RequestMethod.GET,RequestMethod.POST})
    public User selectByPrimaryKey(@PathVariable("id") int id){
        return userService.selectByPrimaryKey(id);
    }

    @ApiOperation(value = "用户登陆",notes = "根据账户密码登陆")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userAccount",value = "账户",required = true,paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "userPassword",value = "密码",required = true,paramType = "query", dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/login", method = {RequestMethod.GET,RequestMethod.POST})
    public boolean userLogin(@PathParam("userAccount") String userAccount, @PathParam("userPassword") String userPassword, HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = userService.userLogin(userAccount, userPassword);
        if (user != null ){
            Class userClass = User.class;
            Field[] fields = userClass.getFields();
            System.out.println(fields.length);
            for (Field field : fields){
                System.out.println(field.getName());
            }
            return true;
        }
        return false;
    }

}
