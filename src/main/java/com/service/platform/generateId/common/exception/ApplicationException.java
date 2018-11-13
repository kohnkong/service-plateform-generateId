package com.service.platform.generateId.common.exception;

import com.service.platform.generateId.common.constants.OperationResult;

public class ApplicationException extends BaseException {

    private static final long serialVersionUID = 1L;

    private String resultMessageCode;

    private Object[] resultMessageArguments;

    private Object outputResult;


    public ApplicationException(OperationResult operationResult, Throwable cause) {
        this(operationResult.getResultCode(), operationResult.getMessageCode(),
                operationResult.getMessageDefault(), null, cause);
    }

    public ApplicationException(OperationResult operationResult, String error) {
        this(operationResult.getResultCode(), operationResult.getMessageCode(),
                operationResult.getMessageDefault());
    }


    public ApplicationException(OperationResult operationResult) {
        this(operationResult.getResultCode(), operationResult.getMessageCode(),
                operationResult.getMessageDefault());
    }

    public ApplicationException(int resultCode, String resultMessageCode,
                                String resultMessage, Object[] resultMessageArguments,
                                Throwable error) {
        super(resultCode, resultMessage, error);
        this.resultMessageCode = resultMessageCode;
        this.resultMessageArguments = resultMessageArguments;
    }

    public ApplicationException(int resultCode, String resultMessageCode,
                                String resultMessage, Throwable error) {
        super(resultCode, resultMessage, error);
        this.resultMessageCode = resultMessageCode;
    }

    public ApplicationException(int resultCode, String resultMessageCode,
                                String resultMessage) {
        super(resultCode, resultMessage, null);
        this.resultMessageCode = resultMessageCode;
    }

    public Object[] getResultMessageArguments() {
        return resultMessageArguments;
    }

    public String getResultMessageCode() {
        return resultMessageCode;
    }

    public ApplicationException setResultMessageCode(String resultMessageCode) {
        this.resultMessageCode = resultMessageCode;
        return this;
    }



    public ApplicationException setResultMessageArguments(
            Object... resultMessageArguments) {
        this.resultMessageArguments = resultMessageArguments;
        return this;
    }

    public boolean isSuccess() {
        return this.resultCode == OperationResult.SUCCESS;
    }

    public ApplicationException setOutputResult(Object outputResult) {
        this.outputResult = outputResult;

        return this;
    }

    public Object getOutputResult() {
        return outputResult;
    }


}
