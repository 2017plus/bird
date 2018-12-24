package com.live.util;

import com.alibaba.druid.util.StringUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.TokenExpiredException;

public class UserHelper {

    /**
     * 在token中解析user用户信息
     * @param token
     * @return
     * @throws Exception
     */
    public static String getUserIdFromToken(String token) throws TokenExpiredException {
        String userId = "";
        if(!StringUtils.isEmpty(token)){
            userId = JWT.decode(token).getAudience().get(0);;
        }
        return userId;
    }
}
