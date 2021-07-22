package com.github.huifer.ews.entity;

import com.github.huifer.ews.operator.HttpMethod;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "url")
public class UrlEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "url", columnDefinition = "路由地址")
	private String url;
	@Column(name = "rule_id", columnDefinition = "规则id")
	private Integer ruleId;

	@Column(name = "http_method", columnDefinition = "http请求方式")
	private HttpMethod httpMethod;

}
