package com.github.huifer.ews.domain.db;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 流程表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`process`")
public class Process implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 场景id
     */
    @TableField(value = "scenes_id")
    private Integer scenesId;

    /**
     * 条件表达式,存储逻辑运算,[rule_id]||[rule_id]
     */
    @TableField(value = "expression")
    private String expression;

    /**
     * 流程名称
     */
    @TableField(value = "`name`")
    private String name;

    /**
     * 流程描述
     */
    @TableField(value = "description")
    private String description;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_SCENES_ID = "scenes_id";

    public static final String COL_EXPRESSION = "expression";

    public static final String COL_NAME = "name";

    public static final String COL_DESCRIPTION = "description";
}