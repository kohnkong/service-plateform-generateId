package com.service.platform.generateId.service.impl;

import com.service.platform.generateId.common.constants.OperationResult;
import com.service.platform.generateId.common.exception.ApplicationException;
import com.service.platform.generateId.constants.Constant;
import com.service.platform.generateId.constants.OperationConstants;
import com.service.platform.generateId.domain.GenerateIdRequest;
import com.service.platform.generateId.domain.RegisterIdCenterRequest;
import com.service.platform.generateId.domain.RequestType;
import com.service.platform.generateId.domain.Response;
import com.service.platform.generateId.domain.model.EntityIdFlow;
import com.service.platform.generateId.mapper.EntityIdFlowMapper;
import com.service.platform.generateId.service.GenerateIdService;
import com.service.platform.generateId.support.GenerateIdServiceSupport;
import com.service.platform.generateId.utils.GsonUtil;
import com.service.platform.generateId.utils.RedisLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GenerateIdServiceImpl implements GenerateIdService {

    @Override
    public String registerIdCenter(RegisterIdCenterRequest registerIdCenterRequest) {

        Response response;
        String operate;

        // 1、查询有没有值  todo 此处需要加缓存
        EntityIdFlow entityIdFlow = entityIdFlowMapper.selectByHeader(StringUtils.isBlank(registerIdCenterRequest.header) ? Constant.DEFAULT_HEAD : registerIdCenterRequest.header);
        // 2、获取分布式锁 ,获取成功后 根据 entityIdFlow 是否为空判断 是更新还是插入
        if(null == entityIdFlow){
            entityIdFlow = new EntityIdFlow(registerIdCenterRequest.header, registerIdCenterRequest.length, Constant.DEFAULT_KEY_BATCH_ID, new Date(), new Date());
            operate = Constant.OPERATE_ADD;
        }else{
            entityIdFlow.setUpdateDate(new Date());
            entityIdFlow.setLength(registerIdCenterRequest.length);
            operate = Constant.OPERATE_UPDATE;
        }

        String key = generateIdServiceSupport.getRegisterKeyLock(registerIdCenterRequest.header);
        if(StringUtils.isBlank(registerIdCenterRequest.header)){
            entityIdFlow.setHead(Constant.DEFAULT_HEAD);
        }
        Boolean result = this.registerIdCenter(key, entityIdFlow, operate);

        if(result){

            return GsonUtil.toJson(Response.ok());

        }else{
            return GsonUtil.toJson(Response.error("注册失败！"));
//            throw new ApplicationException(
//                    OperationConstants.REGISER_ID_CENTER_ERROR);
        }


    }

    @Override
    public String generateId(GenerateIdRequest request) {

        // todo  此处根据head查询出的
        EntityIdFlow entityIdFlow = entityIdFlowMapper.selectByHeader(StringUtils.isBlank(request.header) ? Constant.DEFAULT_HEAD : request.header);

        logger.info("=================GenerateId   begin=====================");
//        logger.info("请求参数为：" + request.header + "------" + request.length);

        Response response;
        String key = generateIdServiceSupport.getKey(StringUtils.isBlank(request.header) ? Constant.DEFAULT_HEAD : request.header);
        // 1、根据key 查询redis中 ID 队列数量  比较阀值， 是否需要生产ID
        // 获取 redis 现存ID 数量
        Long residue  = redisTemplate.opsForSet().size(key);
        String index;
        if(residue < threshold){
            this.produceId(generateIdServiceSupport.getKeyLock(request.getHeader()), request.getHeader());
        }

        residue  = redisTemplate.opsForSet().size(key);
        if(residue > 0){
            // 2、不需要生产 随机取出一个 值 并将该值从Redis删除  返回
            if(request.type == RequestType.Order){
                Set set = (Set) redisTemplate.opsForSet().members(key);

                List list = new ArrayList(set);

                index = (String)Collections.min(list);

                redisTemplate.opsForSet().remove(key, index);


            }else if(request.type == RequestType.Disorder){
                index = (String) redisTemplate.opsForSet().pop(key);
            }else{
                index = (String) redisTemplate.opsForSet().pop(key);
            }

            String prefix =  request.getHeader();
            int minLenth = request.header.length() + String.valueOf(index).length();
            if(entityIdFlow.getLength() < minLenth){

                response = Response.error("长度错误，最小长度为" + minLenth);
                logger.error("长度错误，最小长度为" + minLenth);

//                throw new ApplicationException(new OperationConstants(OperationResult.FAILED, "I009002",
//                        "长度错误，最小长度为" + minLenth));


            }else{
                int coverageLenth = entityIdFlow.getLength() - request.header.length();
                response = Response.ok(prefix.concat(String.format("%1$0" + coverageLenth +"d", Integer.parseInt(index))));
            }

            logger.info("生成结果为：" + GsonUtil.toJson(response));
            logger.info("=================GenerateId   end=====================");
        }else{
            response = Response.error("ID正在生产中!");

//            throw new ApplicationException(
//                    OperationConstants.ID_PRODUCING);
        }

        return GsonUtil.toJson(response);
    }


    private boolean produceId(String key, String head){
        boolean result = false;

        int batchId = 0;

        if(StringUtils.isBlank(head)){
            head = Constant.DEFAULT_HEAD;
        }

        RedisLock redisLock = new RedisLock(stringRedisTemplate, key, expireTime, timeout);
        try {
            long now = System.currentTimeMillis();
            if (redisLock.lock()) {

                EntityIdFlow entityIdFlow = entityIdFlowMapper.selectByHeader(head);
                if(null == entityIdFlow){
//                    entityIdFlow = new EntityIdFlow(head, Constant.DEFAULT_KEY_BATCH_ID);
                    entityIdFlow = new EntityIdFlow();
                    batchId = Constant.DEFAULT_KEY_BATCH_ID;
                    entityIdFlowMapper.insertSelective(entityIdFlow);

                }else{
                    batchId = entityIdFlow.getBatchId() + 1;
                    entityIdFlow.setBatchId(batchId);
                    entityIdFlowMapper.updateByPrimaryKeySelective(entityIdFlow);

                }

                SetOperations set = this.getIds(batchId, head);
                redisTemplate.opsForSet().add(generateIdServiceSupport.getKey(head), set);

            } else {
                result =  false;
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        } finally {
            redisLock.unlock();
        }

        return result;
    }


    private boolean registerIdCenter(String key, EntityIdFlow entityIdFlow, String operate){

        boolean result = false;

        int affecteRow = 0;

        RedisLock redisLock = new RedisLock(stringRedisTemplate, key, expireTime, timeout);
        try {
            long now = System.currentTimeMillis();
            if (redisLock.lock()) {
                if(operate.equals(Constant.OPERATE_ADD)){
                    affecteRow =  entityIdFlowMapper.insertSelective(entityIdFlow);
                }else{
                    affecteRow = entityIdFlowMapper.updateByPrimaryKeySelective(entityIdFlow);
                }

                if(affecteRow == 1){
                    result = true;
                }
                // todo 更新成功后写入redis

            } else {
                result =  false;
            }
        } catch (Exception e) {
            logger.info(e.getMessage(), e);
        } finally {
            redisLock.unlock();
        }

        return result;
    }


    private SetOperations getIds(int batchId, String head){


        SetOperations<String, String> set = redisTemplate.opsForSet();

        for(int i = ((batchId - 1) * increment); i < batchId * increment; i++){
            set.add(generateIdServiceSupport.getKey(head), i + "");
        }
        return set;
    }





    private static final Logger logger = LoggerFactory.getLogger(GenerateIdService.class);

    @Value("${produce.increment}")
    private int increment;

    @Value("${produce.threshold}")
    private int threshold;

    @Value("${produce.expiretime}")
    private int expireTime;

    @Value("${produce.timeout}")
    private int timeout;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private GenerateIdServiceSupport generateIdServiceSupport;

    @Autowired
    private EntityIdFlowMapper entityIdFlowMapper;

}
