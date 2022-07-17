package com.licon.redis.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.licon.redis.core.security.authority.VoteConverter;
import com.licon.redis.core.security.authority.VoteType;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static javax.persistence.ConstraintMode.NO_CONSTRAINT;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/7/4 10:05
 */
@Entity
@Getter
@Setter
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_url")
@Accessors(chain = true)
@Audited
public class Url implements Serializable {
	private static final long serialVersionUID = 2871205415113808479L;

	@Id
	@org.springframework.data.annotation.Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "url_path",nullable = false)
	private String urlPath;

	@Column(name = "url_describe")
	private String urlDescribe;

	@Column(name = "vote_type",nullable = false)
	@Convert(converter = VoteConverter.class)
	private VoteType voteType;

	@NotAudited
	@Builder.Default
	@JsonIgnore
	@Fetch(FetchMode.JOIN)
	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(
			name = "t_url_authority",
			joinColumns = @JoinColumn(name = "url_id", referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"),
			foreignKey = @ForeignKey(NO_CONSTRAINT),
			inverseForeignKey = @ForeignKey(NO_CONSTRAINT))
	private Set<Authority> authorities = new HashSet<>();

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
				"id = " + id + ", " +
				"urlPath = " + urlPath + ", " +
				"urlDescribe = " + urlDescribe + ", " +
				"voteType = " + voteType + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Url url = (Url) o;
		return id != null && Objects.equals(id, url.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}
