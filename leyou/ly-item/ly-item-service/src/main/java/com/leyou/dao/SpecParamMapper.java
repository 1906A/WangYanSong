package com.leyou.dao;

import com.leyou.pojo.SpecParam;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SpecParamMapper extends Mapper<SpecParam> {
    List<SpecParam> findAllParams(@Param("groupId") Long groupId);

    void addSpecParam(SpecParam specParam);

    void updateSpecParam(SpecParam specParam);

    List<SpecParam> findParamByCid(Long cid);

}
