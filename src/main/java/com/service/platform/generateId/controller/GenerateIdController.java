package com.service.platform.generateId.controller;

import com.service.platform.generateId.domain.GenerateIdRequest;
import com.service.platform.generateId.domain.RequestType;
import com.service.platform.generateId.service.GenerateIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateIdController {

    @RequestMapping("test")
    public String test(){

        GenerateIdRequest request = new GenerateIdRequest();
        request.setHeader("XY");
//        request.setLength(10);
        request.setType(RequestType.Order);
//        return Response.ok(service.generateId(request));
        return service.generateId(request);
    }

    @Autowired
    private GenerateIdService service;
}
