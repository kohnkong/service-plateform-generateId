package com.service.platform.generateId.common.exception;


public class NetworkDisconnectSystemException extends SystemException {


    public NetworkDisconnectSystemException(String resultMessage) {
        super(resultMessage);

    }

    public NetworkDisconnectSystemException(String resultMessage, Throwable cause) {
        super(resultMessage, cause);

    }

}
