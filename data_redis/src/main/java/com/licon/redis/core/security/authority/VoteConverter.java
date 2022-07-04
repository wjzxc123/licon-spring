package com.licon.redis.core.security.authority;

import javax.persistence.AttributeConverter;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/7/4 10:24
 */
public class VoteConverter implements AttributeConverter<VoteType,Integer> {
	@Override
	public Integer convertToDatabaseColumn(VoteType attribute) {
		if (attribute == null){
			throw new RuntimeException("Unknown VoteType value : " + attribute);
		}
		return attribute.getCode();
	}

	@Override
	public VoteType convertToEntityAttribute(Integer dbData) {
		return VoteType.getVote(dbData);
	}
}
