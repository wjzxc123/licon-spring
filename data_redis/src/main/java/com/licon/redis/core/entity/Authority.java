package com.licon.redis.core.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.envers.Audited;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Objects;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/29 13:08
 */
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_authority")
@Entity
@Audited
public class Authority  implements GrantedAuthority {
	private static final long serialVersionUID = -3519330160016811028L;

	@Id
	@org.springframework.data.annotation.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "authority",nullable = false,unique = true)
	private String authority;

	@Column(name = "authority_name",nullable = false)
	private String authorityName;

	@Column(name = "enable",nullable = false)
	private boolean enable;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Authority authority = (Authority) o;
		return id != null && Objects.equals(id, authority.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
