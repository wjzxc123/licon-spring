package com.licon.redis.core.api.dto;

import com.licon.redis.core.api.validation.aanotation.ValidEmail;
import com.licon.redis.core.api.validation.aanotation.ValidPassword;
import com.licon.redis.core.api.validation.aanotation.ValidPasswordMatch;
import lombok.Data;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author Licon
 */
@Data
@ValidPasswordMatch
public class UserDto implements Serializable {
    private static final long serialVersionUID = 4389855789059280920L;

    @NotNull
    @NotBlank
    @Size(min = 4,max = 50,message = "长度必须在4-50")
    private String username;

    @NotNull
    @ValidPassword
    private String password;

    @NotNull
    private String matchPassword;

    @ValidEmail
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 2,max = 20,message = "长度必须在2-20")
    private String name;
}
