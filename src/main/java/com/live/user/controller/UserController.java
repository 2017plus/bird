package com.live.user.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.live.config.jwt.JwtTokenUtil;
import com.live.config.jwt.PassToken;
import com.live.user.model.User;
import com.live.user.service.UserService;
import com.live.util.AES;
import com.live.util.BasePath;
import com.live.util.Result;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.DigestUtils;
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
    @ApiOperation(value = "查询用户名是否已经注册", notes = "注册防重复", produces = "application/json")
    @RequestMapping(value = "/registerNameCheck", method = RequestMethod.POST)
    public Result registerNameCheck(@RequestParam String loginName) {
        long num = userService.registerUserCheck(1, loginName);
        Result result = new Result();
        if (num > 0) {
            result.setCode(-1);
            result.setNote("用户名已被使用");
        } else {
            result.setCode(100);
            result.setNote("用户名可用");
        }
        return result;
    }

    @PassToken
    @ApiOperation(value = "注册用户", notes = "注册", produces = "application/json")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Result register(@RequestBody JSONObject jsonObject) {
        User user = new User();
        Result result = new Result();
        user.setLoginName(jsonObject.getString("loginName"));
        // 用户名唯一性验证
        if (userService.registerUserCheck(1, jsonObject.getString("loginName")) > 0) {
            result.setCode(-1);
            result.setNote("注册失败,用户名已存在");
            return result;
        }
        // 邮箱唯一性验证
        if (userService.registerUserCheck(3, jsonObject.getString("email")) > 0) {
            result.setCode(-1);
            result.setNote("注册失败,邮箱已注册");
            return result;
        }
        // AES解密
        String password = "";
        try {
            password = AES.desEncrypt(jsonObject.getString("loginPassowrd"));
        } catch (Exception e) {
            result.setCode(-1);
            result.setNote("注册失败，请检查注册信息是否已被使用!");
            e.printStackTrace();
            return result;
        }
        // MD5加密
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setLoginPassword(md5Password);
        user.setUserEmail(jsonObject.getString("email"));
        user.setUserType(3);
        user.setUserCreateTime(new Date());
        Integer num = null;
        try {
            num = userService.register(user);
            if (num > 0) {
                result.setCode(100);
                result.setNote("注册成功");
                result.setResult(new JSONArray());
            } else {
                result.setCode(-1);
                result.setNote("注册失败");
                return result;
            }
        } catch (Exception e) {
            result.setCode(-1);
            result.setNote("注册失败");
            e.printStackTrace();
            return result;
        }
        return result;
    }

    @PassToken
    @ApiOperation(value = "登录", notes = "", produces = "application/json")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody JSONObject paramObject) {
        String loginName = paramObject.getString("loginName");
        User user = userService.queryUserInfo(loginName);
        Result result = new Result();
        String password = "";
        try {
            password = AES.desEncrypt(paramObject.getString("loginPassword"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (user == null) {
            result.setCode(-1);
            result.setNote("用户不存在");
            return result;
        } else if (!md5Password.equals(user.getLoginPassword().trim())) {
            result.setCode(-1);
            result.setNote("用户名或密码错误");
            return result;
        }
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
    public Result logout(@RequestHeader("x-auth-token") String token) {
        String userId = JwtTokenUtil.getUserIdFromToken(token);
        JwtTokenUtil.refreshToken(token, 0);
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
