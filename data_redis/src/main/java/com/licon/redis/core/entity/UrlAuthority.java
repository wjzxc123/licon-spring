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
 * @date 2022/7/4 16:43
 */
@Entity
@Table(name = "t_url_authority")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UrlAuthority {
	@Id
	@org.springframework.data.annotation.Id
	private long id;

	@Column(name = "url_id",nullable = false)
	private long urlId;

	@Column(name = "authority_id",nullable = false)
	private long authorityId;
}
