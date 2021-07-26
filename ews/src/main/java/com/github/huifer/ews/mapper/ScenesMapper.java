package com.github.huifer.ews.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.huifer.ews.domain.db.Scenes;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScenesMapper extends BaseMapper<Scenes> {
    int updateBatch(List<Scenes> list);

    int updateBatchSelective(List<Scenes> list);

    int batchInsert(@Param("list") List<Scenes> list);

    int insertOrUpdate(Scenes record);

    int insertOrUpdateSelective(Scenes record);
}