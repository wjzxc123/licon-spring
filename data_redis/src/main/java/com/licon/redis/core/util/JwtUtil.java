package com.licon.redis.core.util;

import com.licon.redis.core.config.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Licon
 */
@Component
public class JwtUtil {

    @Getter
    private Key key;
    @Getter
    private Key refreshKey;

    private AppProperties appProperties;

    public JwtUtil() {
    }

    public JwtUtil(AppProperties appProperties) {
        this.appProperties = appProperties;
        key = new SecretKeySpec(Base64.getDecoder().decode(appProperties.getJwt().getKey()),SignatureAlgorithm.HS512.getJcaName());
        refreshKey = new SecretKeySpec(Base64.getDecoder().decode(appProperties.getJwt().getRefreshKey()),SignatureAlgorithm.HS512.getJcaName());
    }


    public String createJwtToken(UserDetails userDetails,long timeToExpire){
        return createJwtToken(userDetails,timeToExpire,key);
    }

    public String createJwtToken(UserDetails userDetails,long timeToExpire,Key signKey){
        long cur = System.currentTimeMillis();
        return Jwts.builder()
                .setId("licon")
                .claim("authorities",userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(cur))
                .setExpiration(new Date(cur+timeToExpire))
                .signWith(signKey,SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessToken(UserDetails userDetails){
        return createJwtToken(userDetails,appProperties.getJwt().getAccessTokenExpireTime(),key);
    }

    public String createRefreshToken(UserDetails userDetails){
        return createJwtToken(userDetails,appProperties.getJwt().getRefreshTokenExpireTime(),refreshKey);
    }


    public boolean validateAccessToken(String accessToken){
        return validateToken(accessToken,key);
    }

    public boolean validateRefreshToken(String refreshToken){
        return validateToken(refreshToken,refreshKey);
    }

    public boolean validateToken(String jwt,Key signKey){
        return parsedClaims(jwt,signKey).isPresent();
    }

    public Optional<Claims> parsedClaims(String jwt,Key signKey){
        return Optional.ofNullable(Jwts.parserBuilder()
                .setSigningKey(signKey)
                .build()
                .parseClaimsJws(jwt)
                .getBody());
    }
}
