package com.github.huifer.ews.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huifer.ews.domain.db.ProcessFalse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProcessFalseMapper extends BaseMapper<ProcessFalse> {
	int updateBatch(List<ProcessFalse> list);

	int updateBatchSelective(List<ProcessFalse> list);

	int batchInsert(@Param("list") List<ProcessFalse> list);

	int insertOrUpdate(ProcessFalse record);

	int insertOrUpdateSelective(ProcessFalse record);
}