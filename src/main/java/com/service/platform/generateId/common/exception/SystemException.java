package com.service.platform.generateId.common.exception;

import com.service.platform.generateId.common.constants.OperationResult;

public class SystemException extends BaseException {

    public SystemException(String resultMessage, Throwable cause) {
        super(OperationResult.SYSTEM_ERROR, resultMessage, cause);

    }

    public SystemException(String resultMessage) {

        super(OperationResult.SYSTEM_ERROR, resultMessage);
    }

    public SystemException(Throwable cause) {

        super(OperationResult.SYSTEM_ERROR, cause.getMessage(), cause);
    }

}
