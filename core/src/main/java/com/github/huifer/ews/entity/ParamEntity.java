package com.github.huifer.ews.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

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

}
