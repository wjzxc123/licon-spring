package com.licon.redis.core.api.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.licon.redis.core.api.validation.group.GroupA;
import com.licon.redis.core.api.validation.group.GroupB;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describe: 用户登陆对象
 *
 * @author Licon
 * @date 2022/8/16 14:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto implements Serializable {
	private static final long serialVersionUID = 5108506771940963594L;

	@NotNull(groups = GroupA.class)
	@NotBlank(groups = GroupB.class)
	private String username;

	@NotNull(groups = GroupA.class)
	private String password;
}
