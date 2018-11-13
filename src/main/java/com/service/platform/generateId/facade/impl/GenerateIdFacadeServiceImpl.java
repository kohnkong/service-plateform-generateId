package com.service.platform.generateId.facade.impl;

import com.service.platform.generateId.common.exception.ApplicationException;
import com.service.platform.generateId.domain.GenerateIdRequest;
import com.service.platform.generateId.domain.RegisterIdCenterRequest;
import com.service.platform.generateId.domain.RequestType;
import com.service.platform.generateId.domain.Response;
import com.service.platform.generateId.facade.GenerateIdFacadeService;
import com.service.platform.generateId.service.GenerateIdService;
import com.service.platform.generateId.utils.GsonUtil;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GenerateIdFacadeServiceImpl implements GenerateIdFacadeService.Iface {


    @Override
    public String registerIdCenter(RegisterIdCenterRequest request) throws TException {
            return generateIdService.registerIdCenter(request);

    }

    @Override
    public String generateId(GenerateIdRequest request) throws TException {
        return generateIdService.generateId(request);
    }

    @Autowired
    private GenerateIdService generateIdService;


}
