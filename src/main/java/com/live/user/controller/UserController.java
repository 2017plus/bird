package com.live.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.live.config.jwt.JwtTokenUtil;
import com.live.config.jwt.PassToken;
import com.live.user.model.User;
import com.live.user.service.UserService;
import com.live.util.BasePath;
import com.live.util.Result;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping(value = BasePath.REQUEST_PROJECT_PATH)
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @PassToken
    @ApiOperation(value = "登录", notes = "", produces = "application/json")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody JSONObject paramObject) {
        String loginName = paramObject.getString("loginName");
        String loginPassword = paramObject.getString("loginPassword");
        User user = userService.queryUserInfo(loginName);
        Result result = new Result();
        if (user == null) {
            result.setCode(-1);
            result.setNote("用户不存在");
        } else if (!loginPassword.equals(user.getLoginPassword().trim())) {
            result.setCode(-1);
            result.setNote("用户名或密码错误");
        } else {
            result.setCode(1);
            result.setNote("登录成功");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("loginName", user.getLoginName());
            jsonObject.put("username", user.getUserName());
            jsonObject.put("lastLoginTime", user.getUserLastLoginTime());
            int code = userService.updateUserLastLoginTime(loginName, new Date());
            String token = JwtTokenUtil.createToken(user.getLoginName());
            redisTemplate.opsForValue().set(user.getLoginName(), token);
            redisTemplate.expire(user.getLoginName(), 60, TimeUnit.MINUTES);
            jsonObject.put("token", token);
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(jsonObject);
            result.setResult(jsonArray);
        }
        return result;
    }

    @ApiOperation(value = "查询用户信息", notes = "", produces = "application/json")
    @RequestMapping(value = "/user/queryUserInfo", method = RequestMethod.POST)
    public Result queryUserInfo(@RequestHeader("x-auth-token") String token) {

        String userId = JwtTokenUtil.getUserIdFromToken(token);
        User user = userService.queryUserInfo(userId);
        Result result = new Result();
        if (user != null) {
            result.setCode(1);
            result.setNote("查询成功");
            JSONArray jsonArray = new JSONArray();
            jsonArray.add(user);
            result.setResult(jsonArray);
        } else {
            result.setCode(-1);
            result.setNote("查询失败");
        }
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result logout (@RequestHeader("x-auth-token") String token){
        String userId = JwtTokenUtil.getUserIdFromToken(token);
        JwtTokenUtil.refreshToken(token,0);
        Boolean flag = redisTemplate.delete(userId);
        Result result = new Result();
        if (flag) {
            result.setCode(1);
            result.setNote("注销成功");
        } else {
            result.setCode(-1);
            result.setNote("注销失败");
        }
        return result;
    }
}
