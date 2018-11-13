package com.service.platform.generateId.service;


import com.service.platform.generateId.common.exception.ApplicationException;
import com.service.platform.generateId.domain.GenerateIdRequest;
import com.service.platform.generateId.domain.RegisterIdCenterRequest;

public interface GenerateIdService {

    public String registerIdCenter(RegisterIdCenterRequest registerIdCenterRequest);

    public String generateId(GenerateIdRequest generateIdRequest);

}
