package com.licon.redis.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/29 14:23
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user_authority")
@Entity
public class UserAuthority {
	@Id
	@org.springframework.data.annotation.Id
	@Column(name = "id",unique = true)
	private long id;

	@Column(name = "user_id",nullable = false)
	private long userId;

	@Column(name = "role_id",nullable = false)
	private long roleId;

	@Column(name = "enable",nullable = false)
	private boolean enable;
}
