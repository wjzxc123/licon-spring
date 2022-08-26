package com.licon.redis.core.util;

import com.licon.redis.core.entity.User;
import com.licon.redis.core.repository.persistence.UserRepository;
import lombok.var;
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
    SmsTotp smsTotp;

    @Autowired
    UserRepository userRepository;

    @Test
    void createToptp() throws InvalidKeyException {
        User admin = userRepository.findOptionalByUsername("admin").orElse(new User());
        String mfaKey = admin.getSmsMfaKey();
        var totp = smsTotp.createTotp(mfaKey);
        System.out.println(totp.isPresent());
    }
}
