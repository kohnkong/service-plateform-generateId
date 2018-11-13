package com.service.platform.generateId.config;

import com.service.platform.generateId.facade.GenerateIdFacadeService;
import com.service.platform.generateId.facade.impl.GenerateIdFacadeServiceImpl;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ThriftServer {

    private static final Logger logger = LoggerFactory.getLogger(ThriftServer.class);

    @Value("${thrift.port}")
    private int port;
    @Value("${thrift.minWorkerThreads}")
    private int minThreads;
    @Value("${thrift.maxWorkerThreads}")
    private int maxThreads;

    private TCompactProtocol.Factory protocolFactory;
    private TFramedTransport.Factory transportFactory;

    @Autowired
    private GenerateIdFacadeServiceImpl generateIdFacadeServiceImpl;

    public void init() {
        protocolFactory = new TCompactProtocol.Factory();
        transportFactory = new TFramedTransport.Factory();
    }

    public void start() {
        //TMultiplexedProcessor processor = new TMultiplexedProcessor();
        //processor.registerProcessor(JazzService.class.getSimpleName(), new JazzService.Processor<JazzService.Iface>(hadoopService));
        GenerateIdFacadeService.Processor processor = new GenerateIdFacadeService.Processor<GenerateIdFacadeService.Iface>(generateIdFacadeServiceImpl);
        init();
        try {
            TServerTransport transport = new TServerSocket(port);
            TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(transport);
            tArgs.processor(processor);
            tArgs.protocolFactory(protocolFactory);
            tArgs.transportFactory(transportFactory);
            tArgs.minWorkerThreads(minThreads);
            tArgs.maxWorkerThreads(maxThreads);
            TServer server = new TThreadPoolServer(tArgs);
            logger.info("thrift服务启动成功, 端口={}", port);
            server.serve();
        } catch (Exception e) {
            logger.error("thrift服务启动失败", e);
        }

    }
}
