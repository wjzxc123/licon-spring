package com.licon.redis.core.security.authority;


/**
 * Describe:
 *
 * @author Licon
 * @date 2022/7/4 10:12
 */
public enum VoteType {
	/**
	 * 一个同意
	 */
	Affirmative(1,"一个同意即通过"),

	/**
	 * 大多数同意
	 */
	ConsensusBased(2,"大多数同意即通过"),

	/**
	 * 一个不同意
	 */
	Unanimous(3,"一个否决即不通过");

	private int code;

	private String describe;

	VoteType(int code, String describe) {
		this.code = code;
		this.describe = describe;
	}

	public int getCode() {
		return code;
	}

	public String getDescribe() {
		return describe;
	}

	public static VoteType getVote(int code){
		for (VoteType value : VoteType.values()) {
			if (value.getCode() == code){
				return value;
			}
		}
		return null;
	}
}
