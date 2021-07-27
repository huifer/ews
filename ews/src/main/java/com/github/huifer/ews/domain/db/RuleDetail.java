package com.github.huifer.ews.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 规则详情
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "rule_detail")
public class RuleDetail implements Serializable {
	public static final String COL_CANTRAST_VALUE = "cantrast_value";
	public static final String COL_ID = "id";
	public static final String COL_SCENES_ID = "scenes_id";
	public static final String COL_EXPRESSION = "expression";
	public static final String COL_COMPARISON_VALUE = "comparison_value";
	public static final String COL_OPERATOR = "operator";
	public static final String COL_NAME = "name";
	private static final long serialVersionUID = 1L;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 场景id
	 */
	@TableField(value = "scenes_id")
	private Integer scenesId;
	/**
	 * 值提取式，json_path
	 */
	@TableField(value = "expression")
	private String expression;
	/**
	 * 比较值
	 */
	@TableField(value = "comparison_value")
	private String comparisonValue;
	/**
	 * 运算符
	 */
	@TableField(value = "`operator`")
	private Integer operator;
	/**
	 * 规则名称
	 */
	@TableField(value = "`name`")
	private String name;
}