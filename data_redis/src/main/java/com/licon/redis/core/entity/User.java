package com.licon.redis.core.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static javax.persistence.ConstraintMode.NO_CONSTRAINT;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/20 17:29
 */
@RedisHash(value = "redis-user",timeToLive = 30)
@Getter
@Setter
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
@Entity
@Document(indexName = "es_user")
@Setting(shards = 3,replicas = 3,sortFields = {"id"},sortOrders = Setting.SortOrder.asc)
@Audited
public class User implements UserDetails, Serializable {

	private static final long serialVersionUID = -1662515152392023921L;

	@Id
	@Field(type = FieldType.Keyword)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Field(type = FieldType.Keyword)
	@Column(name = "username",nullable = false,unique = true)
	private String username;

	@Field(type = FieldType.Text,index = false)
	@Column(name = "password_hash",nullable = false,unique = true)
	private String password;

	@Field(type = FieldType.Text,index = false)
	@Column(name = "name")
	private String name;

	@Field(type = FieldType.Integer,index = false)
	@Column(name = "sex")
	private int sex;

	@Field(type = FieldType.Text,index = false)
	@Column(name = "email")
	private String email;

	@Field(type = FieldType.Boolean,index = false)
	@Column(name = "using_mfa",nullable = false)
	@Builder.Default
	private boolean usingMfa = false;

	@JsonIgnore
	@Column(name = "mfa_key",nullable = false)
	private String mfaKey;


	@Column(name = "account_non_expired")
	private boolean accountNonExpired;

	@Column(name = "account_non_locked")
	private boolean accountNonLocked;

	@Column(name = "credentials_non_expired")
	private boolean credentialsNonExpired;

	@Column(name = "enabled",nullable = false)
	private boolean enabled = true;

	@NotAudited
	@Builder.Default
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.PERSIST)
	@Fetch(FetchMode.JOIN)
	@JoinTable(
			name = "t_user_role",
			joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")},
			foreignKey = @ForeignKey(NO_CONSTRAINT),
			inverseForeignKey = @ForeignKey(NO_CONSTRAINT))
	private Set<Role> roles  = new HashSet<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream()
				.flatMap(role-> Stream.concat(
						Stream.of(new SimpleGrantedAuthority(role.getRoleCode())),
						role.getAuthorities().stream()
				))
				.collect(Collectors.toSet());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
				"id = " + id + ", " +
				"username = " + username + ", " +
				"password = " + password + ", " +
				"sex = " + sex + ", " +
				"accountNonExpired = " + accountNonExpired + ", " +
				"accountNonLocked = " + accountNonLocked + ", " +
				"credentialsNonExpired = " + credentialsNonExpired + ", " +
				"enabled = " + enabled + ")";
	}
}
