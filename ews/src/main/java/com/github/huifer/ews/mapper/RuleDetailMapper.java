package com.github.huifer.ews.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huifer.ews.domain.db.RuleDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RuleDetailMapper extends BaseMapper<RuleDetail> {
	int updateBatch(List<RuleDetail> list);

	int updateBatchSelective(List<RuleDetail> list);

	int batchInsert(@Param("list") List<RuleDetail> list);

	int insertOrUpdate(RuleDetail record);

	int insertOrUpdateSelective(RuleDetail record);
}