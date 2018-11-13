package com.service.platform.generateId.common.constants;

import java.util.Map;

public class OperationResultConstants implements OperationResult {

    public final static OperationResult SUCCESS_OPERATION = new OperationResultConstants(
            OperationResult.SUCCESS, "I000000", "Success Operation!");

    public final static OperationResult FAILED_PARAMETER_LOST = new OperationResultConstants(
            OperationResult.BUSINESS_ILLEGAL_PARAMETER, "I000085", "你的传入参数缺失！");

    public final static OperationResult FAILED_PARAMETER_Illegal = new OperationResultConstants(
            OperationResult.BUSINESS_ILLEGAL_PARAMETER, "I000086", "你的传入参数错误！");

    private int resultCode;

    private String messageCode;

    private String messageDefault;


    protected OperationResultConstants(int resultCode, String messageCode,
                                       String msgDefValue) {
        this.resultCode = resultCode;
        this.messageCode = messageCode;
        this.messageDefault = msgDefValue;
    }


    @Override
    public String getMessageCode() {
        return this.messageCode;
    }

    @Override
    public String getMessageDefault() {
        return this.messageDefault;
    }

    @Override
    public int getResultCode() {
        return this.resultCode;
    }

    @Override
    public boolean isSuccess() {
        return this.resultCode == OperationResult.SUCCESS;
    }

    public static Map<String, OperationResult> getOperationResultMap(Class operationResultConstantClazz, boolean deepFetch){

        return null;
    }
}
