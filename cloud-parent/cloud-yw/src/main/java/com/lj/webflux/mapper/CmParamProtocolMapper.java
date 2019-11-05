package com.lj.webflux.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.lj.webflux.entity.CmParamProtocol;

public interface CmParamProtocolMapper {

    List<Map<String, Object>> selectAll();

    int insert(@Param("cm") CmParamProtocol cmParamProtocol);

    int delete(@Param("trainCate") String trainCate);

}
