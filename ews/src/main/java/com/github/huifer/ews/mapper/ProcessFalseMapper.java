package com.github.huifer.ews.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huifer.ews.domain.db.ProcessFalse;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ProcessFalseMapper extends BaseMapper<ProcessFalse> {
    int updateBatch(List<ProcessFalse> list);

    int updateBatchSelective(List<ProcessFalse> list);

    int batchInsert(@Param("list") List<ProcessFalse> list);

    int insertOrUpdate(ProcessFalse record);

    int insertOrUpdateSelective(ProcessFalse record);
}