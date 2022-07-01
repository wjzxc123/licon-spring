package com.licon.redis.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/29 13:08
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_authority")
@Entity
public class Authority  implements GrantedAuthority {
	private static final long serialVersionUID = -3519330160016811028L;

	@Id
	@org.springframework.data.annotation.Id
	private long id;

	@Column(name = "authority",nullable = false,unique = true)
	private String authority;

	@Column(name = "authority_name",nullable = false)
	private String authorityName;

	@Column(name = "enable",nullable = false)
	private boolean enable;
}
