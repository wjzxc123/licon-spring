package com.licon.redis.core.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.licon.redis.core.security.authority.VoteConverter;
import com.licon.redis.core.security.authority.VoteType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/7/4 10:05
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_url")
@Accessors(chain = true)
public class Url {
	@Id
	@org.springframework.data.annotation.Id
	private Long id;

	@Column(name = "url_path",nullable = false)
	private String urlPath;

	@Column(name = "url_describe")
	private String urlDescribe;

	@Column(name = "vote_type",nullable = false)
	@Convert(converter = VoteConverter.class)
	private VoteType voteType;

	@Transient
	private List<Authority> authorities;
}
