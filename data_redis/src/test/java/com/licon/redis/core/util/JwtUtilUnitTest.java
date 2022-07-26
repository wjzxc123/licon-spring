package com.licon.redis.core.util;

import com.licon.redis.core.entity.Authority;
import com.licon.redis.core.entity.Role;
import com.licon.redis.core.entity.User;
import io.jsonwebtoken.Claims;
import org.elasticsearch.core.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class JwtUtilUnitTest {

    JwtUtil jwtUtil;

    @BeforeEach
    public void setUp(){
        jwtUtil = new JwtUtil();
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
        String jwtToken = jwtUtil.createJwtToken(user);
        System.out.println(jwtToken);

        Claims claims = jwtUtil.parsedClaims(jwtToken);
        System.out.println(claims);
    }
}
