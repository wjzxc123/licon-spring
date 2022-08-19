package com.licon.redis.core.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/19 15:08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Result <T> implements Serializable {

	private static final long serialVersionUID = 7462301782618542983L;

	private int code;

	private String msg;

	private T data;

	public static Result<?> ok(Object data){
		return Result.builder().code(200).data(data).msg("ok").build();
	}
}
