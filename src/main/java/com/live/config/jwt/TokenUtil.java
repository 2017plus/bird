package com.live.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

/**
 * 用于生成token
 */
@Service("TokenUtil")
public class TokenUtil {
    public String getToken(String username,String Password) {
        String token="";
        token= JWT.create().withAudience(username)// 将 user id 保存到 token 里面
                .sign(Algorithm.HMAC256(Password));// 以 password 作为 token 的密钥
        return token;
    }
}
