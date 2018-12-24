package com.live.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.live.user.model.User;
import com.live.user.service.UserService;
import com.live.util.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class CheckToken {

    @Autowired
    private UserService userService;

    @PassToken
    @ApiOperation(value="检查token有效性", notes="",produces = "application/json")
    @RequestMapping(value = "/checkToken",method= RequestMethod.POST)
    public Result CheckToken (@RequestHeader("x-auth-token") String token){
        Result result = new Result();
        if (token == null) {
            return result.logout();
        }
        // 获取 token 中的 user id
        String username = "";
        try {
            username = JWT.decode(token).getAudience().get(0);
        } catch (JWTDecodeException j) {
            return result.logout();
        }
        User user = userService.queryUserInfo(username);
        if (user == null) {
            return result.logout();
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getLoginPassword())).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            return result.logout();
        }
        return result.login();
    }
}
