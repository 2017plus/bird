package com.live.config.jwt;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONArray;
import com.live.config.TokenAnalysisErrorException;
import com.live.util.Result;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

import static org.springframework.http.codec.ServerSentEvent.builder;

/**
 * 用于生成token
 */
@Service("JwtTokenUtil")
public class JwtTokenUtil {

    private static String secert = "com.bird";  // 私钥
    private static long timeoutMillis = 1000 * 60 * 30;//过期时间30分钟

    public static SecretKey generalKey(String secert) {
        byte[] encodedKey = secert.getBytes();
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length,
                "HS512");
        return key;
    }

    /**
     * 签发JWT
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public static String createToken(String userId) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        SecretKey secretKey = generalKey(secert);
        return Jwts.builder()
                .setId(userId)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + timeoutMillis))
                .signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    /**
     * 解析JWT字符串
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey(secert);
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (ExpiredJwtException e) {
//            throw new TokenAnalysisErrorException("token已过期"+e.getMessage(), e.getCause());
        } catch (UnsupportedJwtException e) {
//            throw new TokenAnalysisErrorException("token解析失败,非法格式"+e.getMessage(), e.getCause());
        } catch (MalformedJwtException e) {
//            throw new TokenAnalysisErrorException(e.getMessage(), e.getCause());
        } catch (SignatureException e) {
//            throw new TokenAnalysisErrorException("token解析失败"+e.getMessage(), e.getCause());
        }
        return claims;
    }

    /**
     * 在token中解析user用户信息
     *
     * @param token
     * @return
     * @throws Exception
     */
    public static String getUserIdFromToken(String token) throws TokenAnalysisErrorException {
        String userId = "";
        if (!StringUtils.isEmpty(token)) {
            Claims claims = null;
            try {
                claims = parseJWT(token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            userId = claims.getId();
        }
        return userId;
    }

    /**
     * 刷新token
     */
    public static void refreshToken(String token,long timeoutMillis) {
        if (!StringUtils.isEmpty(token)) {
            Claims claims = null;
            try {
                claims = parseJWT(token);
                claims.setExpiration(new Date(System.currentTimeMillis() + timeoutMillis));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


