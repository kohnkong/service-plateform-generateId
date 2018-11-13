package com.service.platform.generateId;


import com.service.platform.generateId.config.ThriftServer;
import com.service.platform.generateId.facade.GenerateIdFacadeService;
import com.service.platform.generateId.facade.impl.GenerateIdFacadeServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan(basePackages = { "com.service.platform.generateId.mapper" })
public class GenerateIdMainApplication {

    private static ThriftServer thriftServer;

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(GenerateIdMainApplication.class, args);

        try {
            thriftServer = context.getBean(ThriftServer.class);
            thriftServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
