package com.licon.auth;

import com.licon.auth.util.JWTUtil;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
class SpringSecurityAuthApplicationTests {

    @Autowired
    JWTUtil jwtUtil;
    @Test
    public void givenUserDetails_thenObtainJwtToken(){
        String username = "licon";
        UserDetails user = User.builder()
                .username(username)
                .authorities("admin", "user")
                .password("1245678")
                .build();
        String jwtToken = jwtUtil.createJwtToken(user);
        System.out.println(jwtToken);

        Claims claims = jwtUtil.parsedClaims(jwtToken);
        System.out.println(claims);
    }

}
