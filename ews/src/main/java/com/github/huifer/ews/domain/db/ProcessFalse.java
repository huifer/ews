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
 * 流程失败处理表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "process_false")
public class ProcessFalse implements Serializable {
	public static final String COL_ID = "id";
	public static final String COL_PROCESS_ID = "process_id";
	public static final String COL_ACTION_ID = "action_id";
	private static final long serialVersionUID = 1L;
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 处理流程id
	 */
	@TableField(value = "process_id")
	private Integer processId;
	/**
	 * 处理行为id
	 */
	@TableField(value = "action_id")
	private Integer actionId;
}