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
 * @date 2022/7/1 17:48
 */
@Entity
@Table("t_role")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	@Id
	@org.springframework.data.annotation.Id
	private long id;

	@Column(name = "role_code",unique = true,nullable = false)
	private String roleCode;

	@Column(name = "role_name",nullable = false)
	private String roleName;

	@Column(name = "enable",nullable = false)
	private boolean enable;
}
