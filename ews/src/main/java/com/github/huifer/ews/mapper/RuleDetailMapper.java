package com.github.huifer.ews.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huifer.ews.domain.db.RuleDetail;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RuleDetailMapper extends BaseMapper<RuleDetail> {
    int updateBatch(List<RuleDetail> list);

    int updateBatchSelective(List<RuleDetail> list);

    int batchInsert(@Param("list") List<RuleDetail> list);

    int insertOrUpdate(RuleDetail record);

    int insertOrUpdateSelective(RuleDetail record);
}