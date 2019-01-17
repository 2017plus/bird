package com.live.config.jwt;

import com.alibaba.fastjson.JSONObject;
import com.live.util.Result;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;


public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        //解决跨域
//        String origin = httpServletRequest.getHeader("Origin");
//        httpServletResponse.setHeader("Access-Control-Allow-Origin","http://localhost:3000");
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "PUT,POST,GET,DELETE");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Accept,token,X-Requested-With");
//        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
        String token = httpServletRequest.getHeader("x-auth-token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        // 执行认证
        if (token == null || token.equals("")) {
            // token未传，token为空
            Result result = new Result();
            result.fail("token不能为空");
            errorResult(httpServletResponse, JSONObject.toJSONString(result));
            return false;
        }
        // 获取 token 中的 user id
        Claims claims = JwtTokenUtil.parseJWT(token);
        if(claims==null||claims.isEmpty()){
            String json = JSONObject.toJSONString(new Result().fail("token已失效"));
            errorResult(httpServletResponse, json);
            return false;
        }
        String userId = claims.getId();
        Boolean hasKey = redisTemplate.hasKey(userId);
        Boolean tokenValid = false;
        if (hasKey) {
            tokenValid = token.equals((String) redisTemplate.opsForValue().get(userId));
            if (!tokenValid) {
                errorResult(httpServletResponse, JSONObject.toJSONString(new Result().fail("token已失效")));
                return false;
            }
        } else {
            errorResult(httpServletResponse, JSONObject.toJSONString(new Result().fail("token已失效")));
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        Result result = new Result();
    }

    public void errorResult(HttpServletResponse response, String json) throws Exception {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        try {
            writer = response.getWriter();
            writer.print(json);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}
