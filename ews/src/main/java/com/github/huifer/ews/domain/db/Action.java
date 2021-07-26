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
 * 动作表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`action`")
public class Action implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 场景id
     */
    @TableField(value = "scenes_id")
    private Integer scenesId;

    /**
     * http请求方式
     */
    @TableField(value = "http_method")
    private String httpMethod;

    /**
     * 请求地址
     */
    @TableField(value = "url")
    private String url;

    /**
     * 请求示例
     */
    @TableField(value = "example")
    private String example;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_SCENES_ID = "scenes_id";

    public static final String COL_HTTP_METHOD = "http_method";

    public static final String COL_URL = "url";

    public static final String COL_EXAMPLE = "example";
}