package com.service.platform.generateId.constants;

import com.service.platform.generateId.common.constants.OperationResult;
import com.service.platform.generateId.common.constants.OperationResultConstants;

public class OperationConstants extends OperationResultConstants {

    public OperationConstants(int resultCode, String messageCode, String msgDefValue) {
        super(resultCode, messageCode, msgDefValue);
    }

    public final static OperationResult REGISER_ID_CENTER_ERROR = new OperationConstants(
            OperationResult.FAILED, "I009001",
            "注册失败！");

//    public final static OperationResult ID_LENGTH_ERROR = new OperationConstants(
//            OperationResult.FAILED, "I009002",
//            "ID长度错误");


    public final static OperationResult ID_PRODUCING = new OperationConstants(
            OperationResult.FAILED, "I009003",
            "ID正在生产中!");




}
