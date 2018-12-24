package com.live.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.live.config.jwt.PassToken;
import com.live.config.jwt.TokenUtil;
import com.live.config.jwt.UserLoginToken;
import com.live.user.model.User;
import com.live.user.service.UserService;
import com.live.util.BasePath;
import com.live.util.Result;
import com.live.util.UserHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value=BasePath.REQUEST_PROJECT_PATH)
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtil tokenUtil;

    @PassToken
    @RequestMapping(value="/login",method= RequestMethod.POST)
    public Result login(
            @RequestParam(value = "loginName") String loginName,
            @RequestParam(value = "loginPassword") String loginPassword) {
        User user = userService.queryUserInfo(loginName);
        Result result = new Result();
        if(user==null){
            result.setCode(-1);
            result.setNote("用户不存在");
        }else if(!loginPassword.equals(user.getLoginPassword().trim())){
            result.setCode(-1);
            result.setNote("用户名或密码错误");
        }else{
            result.setCode(1);
            result.setNote("登录成功");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("loginName",user.getLoginName());
            jsonObject.put("username",user.getUserName());
            jsonObject.put("lastLoginTime",user.getUserLastLoginTime());
            jsonObject.put("token",tokenUtil.getToken(user.getLoginName(),user.getLoginPassword()));
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            result.setResult(jsonArray);
        }
        return result;
    }

    @RequestMapping(value = "/user/queryUserInfo",method = RequestMethod.POST)
    public Result queryUserInfo(@RequestHeader("x-auth-token") String token){
        String userId = UserHelper.getUserIdFromToken(token);
        User user = userService.queryUserInfo(userId);
        Result result = new Result();
        if(user!=null){
            result.setCode(1);
            result.setNote("查询成功");
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(user);
            result.setResult(jsonArray);
        }else{
            result.setCode(-1);
            result.setNote("查询失败");
        }
        return result;
    }
}
