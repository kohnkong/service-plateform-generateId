package com.service.platform.generateId.thrift;

import com.google.gson.Gson;
import com.service.platform.generateId.domain.GenerateIdRequest;
import com.service.platform.generateId.domain.RegisterIdCenterRequest;
import com.service.platform.generateId.domain.RequestType;
import com.service.platform.generateId.domain.Response;
import com.service.platform.generateId.facade.GenerateIdFacadeService;
import com.service.platform.generateId.utils.GsonUtil;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class ThriftRemoteTest {

    public static void main(String[] args) {
        try {
            TTransport transport = new TSocket("localhost", 9000, 300000);

            transport = new TFramedTransport.Factory().getTransport(transport);

            TCompactProtocol protocol = new TCompactProtocol(transport);
            GenerateIdFacadeService.Client client = new GenerateIdFacadeService.Client(protocol);


            transport.open();
            RegisterIdCenterRequest registerIdCenterRequestequest = new RegisterIdCenterRequest();
            registerIdCenterRequestequest.setHeader("");
            registerIdCenterRequestequest.setLength(6);

            GenerateIdRequest generateIdRequest = new GenerateIdRequest();
            generateIdRequest.setHeader("");
            generateIdRequest.setType(RequestType.Disorder);

//            System.out.println("远程调用服务：" + client.registerIdCenter(registerIdCenterRequestequest));
            System.out.println("远程调用服务：" + client.generateId(generateIdRequest));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
