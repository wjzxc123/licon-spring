package com.licon.redis.core.api.dto;

import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.licon.redis.core.api.validation.annotation.ValidEnumValue;
import com.licon.redis.core.api.validation.group.GroupA;
import com.licon.redis.core.api.validation.group.GroupB;
import com.licon.redis.core.config.CustomJackSon;
import com.licon.redis.core.type.MfaType;
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

	@ValidEnumValue(enumClazz = MfaType.class,values = {"SMS","CODE"},message = "{ValidEnumValue.MfaType}",groups = GroupA.class)
	@NotNull(groups = GroupB.class)
	@JsonDeserialize(using = CustomJackSon.MfaTypeDeserializer.class)
	private MfaType mfaType;

}
