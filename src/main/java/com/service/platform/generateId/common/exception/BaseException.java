package com.service.platform.generateId.common.exception;

@SuppressWarnings("serial")
public abstract class BaseException extends RuntimeException {

    public static final int UNKOWN_ERROR_CODE = 99;

    public static final String UNKONN_ERROR_MESSAGE = "Unkown Exception";

    protected int resultCode = UNKOWN_ERROR_CODE;

    protected String resultMessage = UNKONN_ERROR_MESSAGE;

    public BaseException(int resultCode, String resultMessage, Throwable cause){
        super(resultMessage, cause);
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public BaseException(int resultCode, String resultMessage){
        super(resultMessage);
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public String getResultMessage(){
        return resultMessage;
    }

    public int getResultCode(){
        return resultCode;
    }

    public BaseException setResultMessage(String resultMessage){
        this.resultMessage = resultMessage;
        return this;
    }

}
