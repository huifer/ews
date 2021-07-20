package com.github.huifer.ews.entity;

import com.github.huifer.ews.operator.LogicalOperators;
import com.github.huifer.ews.operator.OperatorEnums;
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
   * 逻辑运算符
   */
  @Column(name = "logical_operator", columnDefinition = "逻辑运算符")
  private LogicalOperators logicalOperator;

  /**
   * 比较值
   */
  @Column(name = "rel_value", columnDefinition = "比较值")
  private String relValue;

  @Column(name = "role_id", columnDefinition = "规则id")
  private Integer roleId;
}
