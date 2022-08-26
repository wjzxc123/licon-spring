package com.licon.redis.core.util;

import com.licon.redis.core.config.AppProperties;
import com.licon.redis.core.entity.Authority;
import com.licon.redis.core.entity.Role;
import com.licon.redis.core.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.elasticsearch.core.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import java.security.InvalidKeyException;
import java.security.Key;
import java.util.Base64;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class JwtUtilUnitTest {

    JwtUtil jwtUtil;
    AppProperties appProperties;


    @BeforeEach
    public void setUp(){
        appProperties = new AppProperties();
        SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        SecretKey refreshKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        AppProperties.Jwt jwt = new AppProperties.Jwt();
        jwt.setKey(Base64.getEncoder().encodeToString(key.getEncoded()));
        jwt.setRefreshKey(Base64.getEncoder().encodeToString(refreshKey.getEncoded()));
        appProperties.setJwt(jwt);
        jwtUtil = new JwtUtil(appProperties);
    }
    @Test
    public void givenUserDetails_thenObtainJwtToken(){
        String username = "admin";
        User user = User.builder()
                .username(username)
                .sex(1)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .roles(
                        Set.of(
                            Role.builder()
                                    .roleCode("ADMIN")
                                    .roleName("管理员")
                                    .enable(true)
                                    .authorities(
                                            Set.of(
                                                Authority.builder()
                                                        .authority("read")
                                                        .authorityName("读")
                                                        .enable(true)
                                                        .build(),
                                                Authority.builder()
                                                        .authority("write")
                                                        .authorityName("写")
                                                        .enable(true)
                                                        .build()
                                            )
                                    )
                                    .build(),
                            Role.builder()
                                    .roleCode("USER")
                                    .roleName("用户")
                                    .enable(true)
                                    .authorities(
                                            Set.of(
                                                Authority.builder()
                                                        .authority("read")
                                                        .authorityName("读")
                                                        .enable(true)
                                                        .build()
                                            )
                                    )
                                    .build()
                        )
                )
                .build();
        String jwtToken = jwtUtil.createJwtToken(user,appProperties.getJwt().getAccessTokenExpireTime());
        System.out.println(jwtToken);

        Optional<Claims> claims = jwtUtil.parsedClaims(jwtToken, jwtUtil.getKey());
        claims.ifPresent(System.out::println);
    }

    @Test
    void verify_code() throws InvalidKeyException {

    }
}
