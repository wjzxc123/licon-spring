package com.licon.redis.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/7/1 17:55
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_role_authority")
@Entity
public class RoleAuthority {
	@Id
	@org.springframework.data.annotation.Id
	@Column(name = "id",unique = true)
	private long id;

	@Column(name = "role_id",nullable = false)
	private long roleId;

	@Column(name = "authority_id",nullable = false)
	private long authorityId;

	@Column(name = "enable",nullable = false)
	private boolean enable;
}
