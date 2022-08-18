package com.licon.redis.core.api.dto;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/18 9:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyTotpDto {
	@NotNull
	private String mfaId;

	@NotNull
	private String code;

}
