package com.licon.redis.core.util;

import java.util.Random;

import lombok.val;

import org.springframework.stereotype.Component;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/8/16 15:40
 */
@Component
public class CryptoUtil {

	public String randomAlphanumeric(int stringLength){
		int leftLimit = 48; // 0
		int rightLimit = 122; // z

		val random = new Random();
		return random.ints(leftLimit,rightLimit+1)
				.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97)) // 过滤掉 Unicode 65 和 90 之间的字符
				.limit(stringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				.toString();
	}
}
