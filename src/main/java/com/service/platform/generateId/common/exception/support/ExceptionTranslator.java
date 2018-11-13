package com.service.platform.generateId.common.exception.support;

import com.service.platform.generateId.common.exception.BaseException;

public interface ExceptionTranslator {

    public BaseException translateException(Throwable error);

}
