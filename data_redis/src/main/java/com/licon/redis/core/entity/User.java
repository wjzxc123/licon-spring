package com.licon.redis.core.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.*;
import org.hibernate.envers.Audited;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.redis.core.RedisHash;

import static org.hibernate.envers.RelationTargetAuditMode.NOT_AUDITED;

/**
 * Describe:
 *
 * @author Licon
 * @date 2022/6/20 17:29
 */
@RedisHash(value = "redis-user",timeToLive = 30)
@Getter
@Setter
@ToString
@Builder
@With
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "t_user")
@Entity
@Document(indexName = "es_user")
@Setting(shards = 3,replicas = 3,sortFields = {"id"},sortOrders = Setting.SortOrder.asc)
@Audited
public class User{

	@Id
	@javax.persistence.Id
	@Field(type = FieldType.Keyword)
	Long id;

	@Field(type = FieldType.Keyword)
	@Column(name = "username",nullable = false,unique = true)
	String username;

	@Field(type = FieldType.Text,index = false)
	@Column(name = "password",nullable = false,unique = true)
	String password;

	@Field(type = FieldType.Integer,index = false)
	@Column(name = "sex")
	int sex;

	@Column(name = "account_expired")
	boolean accountExpired;

	@Column(name = "account_locked")
	boolean accountLocked;

	@Column(name = "credentials_expired")
	boolean credentialsExpired;

	@Column(name = "enable")
	boolean enable;

	@Transient
	@Audited(targetAuditMode = NOT_AUDITED)
	private List<Authority> authorities;

}
