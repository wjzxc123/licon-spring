package com.licon.redis.core.config;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.licon.redis.core.type.MfaType;

import org.springframework.boot.jackson.JsonComponent;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/26 22:52
 */
@JsonComponent
public class CustomJackSon {
	public static class MfaTypeDeserializer extends JsonDeserializer<MfaType>{

		@Override
		public MfaType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
			return MfaType.valueOf(p.getIntValue());
		}
	}
}
