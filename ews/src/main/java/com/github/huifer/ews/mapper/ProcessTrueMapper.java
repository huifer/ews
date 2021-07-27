package com.github.huifer.ews.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huifer.ews.domain.db.ProcessTrue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ProcessTrueMapper extends BaseMapper<ProcessTrue> {
	int updateBatch(List<ProcessTrue> list);

	int updateBatchSelective(List<ProcessTrue> list);

	int batchInsert(@Param("list") List<ProcessTrue> list);

	int insertOrUpdate(ProcessTrue record);

	int insertOrUpdateSelective(ProcessTrue record);
}