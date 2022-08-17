package com.licon.redis.core.util;

import com.licon.redis.core.entity.User;
import com.licon.redis.core.repository.persistence.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.security.InvalidKeyException;
import java.time.Instant;

@SpringBootTest
@ActiveProfiles("hom")
public class TotpUtilTest {

    @Autowired
    TotpUtil totpUtil;

    @Autowired
    UserRepository userRepository;

    @Test
    void createToptp() throws InvalidKeyException {
        User admin = userRepository.findOptionalByUsername("admin").orElse(new User());
        String mfaKey = admin.getMfaKey();
        String totp = totpUtil.createTotp(totpUtil.decodeKeyFromString(mfaKey), Instant.now());
        System.out.println(totp);
    }
}
