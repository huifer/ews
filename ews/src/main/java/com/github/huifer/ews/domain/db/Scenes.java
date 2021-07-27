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
 * 场景表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "scenes")
public class Scenes implements Serializable {
	public static final String COL_ID = "id";
	public static final String COL_NAME = "name";
	public static final String COL_EXAMPLE_JSON = "example_json";
	public static final String COL_DESCRIPTION = "description";
	private static final long serialVersionUID = 1L;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 场景名称
	 */
	@TableField(value = "`name`")
	private String name;
	/**
	 * 示例json
	 */
	@TableField(value = "example_json")
	private String exampleJson;
	/**
	 * 场景描述
	 */
	@TableField(value = "description")
	private String description;
}