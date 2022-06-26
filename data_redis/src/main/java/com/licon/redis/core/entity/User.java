package com.licon.redis.core.entity;


import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.*;
import org.hibernate.envers.Audited;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.redis.core.RedisHash;

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
	String username;
	@Field(type = FieldType.Text,index = false)
	String password;
	@Field(type = FieldType.Integer,index = false)
	int sex;
}
