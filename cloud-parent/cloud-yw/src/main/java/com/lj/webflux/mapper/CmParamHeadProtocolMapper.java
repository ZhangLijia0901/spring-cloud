package com.lj.webflux.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lj.webflux.entity.CmParamHeadProtocol;

public interface CmParamHeadProtocolMapper {

    int insert(CmParamHeadProtocol cmParamHeadProtocol);

    List<CmParamHeadProtocol> selectAll();

    int delete(@Param("trainCate") String trainCate);
}
