package com.licon.redis.core.api.dto;

import com.licon.redis.core.api.validation.aanotation.ValidEmail;
import com.licon.redis.core.api.validation.aanotation.ValidMobile;
import com.licon.redis.core.api.validation.aanotation.ValidPassword;
import com.licon.redis.core.api.validation.aanotation.ValidPasswordMatch;
import com.licon.redis.core.api.validation.group.GroupA;
import com.licon.redis.core.api.validation.group.GroupB;
import com.licon.redis.core.api.validation.group.GroupC;
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

    @NotNull(groups = GroupA.class)
    @NotBlank(groups = GroupB.class)
    @Size(min = 4,max = 50,message = "长度必须在4-50")
    private String username;

    @NotNull(groups = GroupA.class)
    @ValidPassword
    private String password;

    @NotNull(groups = GroupA.class)
    private String matchPassword;

    @ValidEmail(groups = GroupC.class)
    @NotNull(groups = GroupA.class)
    @NotBlank(groups = GroupB.class)
    private String email;

    @ValidMobile(groups = GroupC.class)
    @NotNull(groups = GroupA.class)
    @NotBlank(groups = GroupB.class)
    private String mobile;

    @NotNull(groups = GroupA.class)
    @NotBlank(groups = GroupB.class)
    @Size(min = 2,max = 20,message = "长度必须在2-20")
    private String name;
}
