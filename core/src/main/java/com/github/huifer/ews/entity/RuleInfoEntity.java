package com.github.huifer.ews.entity;

import com.github.huifer.ews.operator.OperatorEnums;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "rule_info")
public class RuleInfoEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/**
	 * 值表达式
	 */
	@Column(name = "value_expression", columnDefinition = "值表达式")
	private String valueExpression;
	/**
	 * 运算符
	 */
	@Column(name = "operator_enum", columnDefinition = "运算符")
	private OperatorEnums operatorEnum;
	/**
	 * 比较值
	 */
	@Column(name = "rel_value", columnDefinition = "比较值")
	private String relValue;
	@Column(name = "rule_id", columnDefinition = "规则id")
	private Integer ruleId;

}
