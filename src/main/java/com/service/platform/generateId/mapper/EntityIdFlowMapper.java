package com.service.platform.generateId.mapper;

import com.service.platform.generateId.domain.model.EntityIdFlow;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

@Repository
public interface EntityIdFlowMapper extends Mapper<EntityIdFlow> {

    public EntityIdFlow selectByHeader(String header);


}