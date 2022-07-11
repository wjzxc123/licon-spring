package com.licon.redis.core.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static  org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("hom")
public class SecurityRestApiTest {
    @Autowired
    WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    /**
     *  describe:@WithMockUser 虚拟用户，假设已经认证
     * @throws Exception
     */
    @WithMockUser
    @Test
    public void givenAuthRequest_shouldSuccessWith200() throws Exception {
        mockMvc.perform(
                get("/authority/getAuth")
        )
                .andExpect(status().isOk());
    }
}
