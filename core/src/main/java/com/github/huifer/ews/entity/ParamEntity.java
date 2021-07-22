package com.github.huifer.ews.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "param")
public class ParamEntity implements Serializable {


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "url_id", columnDefinition = "规则名称")
	private Integer urlId;
	@Column(name = "source", columnDefinition = "数据源取值")
	private String source;
	@Column(name = "target", columnDefinition = "数据目标键")
	private String target;
	@Column(name = "default_value", columnDefinition = "默认值")
	private String defaultValue;
}
