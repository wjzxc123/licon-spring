package com.licon.redis.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.ConstraintMode.NO_CONSTRAINT;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/7/1 17:48
 */
@Entity
@Table(name = "t_role")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Audited
public class Role implements Serializable {
	private static final long serialVersionUID = 5782897030912852211L;

	@Id
	@org.springframework.data.annotation.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "role_code",unique = true,nullable = false)
	private String roleCode;

	@Column(name = "role_name",nullable = false)
	private String roleName;

	@Column(name = "enable",nullable = false)
	private boolean enable;

	@NotAudited
	@Builder.Default
	@JsonIgnore
	@Fetch(FetchMode.JOIN)
	@ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
	@JoinTable(
			name = "t_role_authority",
			joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"),
			foreignKey = @ForeignKey(NO_CONSTRAINT),
			inverseForeignKey = @ForeignKey(NO_CONSTRAINT))
	private Set<Authority> authorities = new HashSet<>();

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
				"id = " + id + ", " +
				"roleCode = " + roleCode + ", " +
				"roleName = " + roleName + ", " +
				"enable = " + enable + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Role role = (Role) o;
		return id != null && Objects.equals(id, role.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
