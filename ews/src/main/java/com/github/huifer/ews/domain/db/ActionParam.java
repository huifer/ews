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
 * 动作参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "action_param")
public class ActionParam implements Serializable {
	public static final String COL_ID = "id";
	public static final String COL_ACTION_ID = "action_id";
	public static final String COL_EXPRESSION = "expression";
	public static final String COL_TARGET = "target";
	public static final String COL_DEFAULT_VALUE = "default_value";
	private static final long serialVersionUID = 1L;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 动作id
	 */
	@TableField(value = "action_id")
	private Integer actionId;
	/**
	 * 值提取式，json_path
	 */
	@TableField(value = "expression")
	private String expression;
	/**
	 * 目标键，properties表达式
	 */
	@TableField(value = "target")
	private String target;
	/**
	 * 默认值
	 */
	@TableField(value = "default_value")
	private String defaultValue;
}