package com.github.huifer.ews.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huifer.ews.domain.db.ActionParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ActionParamMapper extends BaseMapper<ActionParam> {
	int updateBatch(List<ActionParam> list);

	int updateBatchSelective(List<ActionParam> list);

	int batchInsert(@Param("list") List<ActionParam> list);

	int insertOrUpdate(ActionParam record);

	int insertOrUpdateSelective(ActionParam record);
}