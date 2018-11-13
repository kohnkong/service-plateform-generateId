package com.service.platform.generateId.common.constants;

public interface OperationResult {

    public static final int SUCCESS = 0;
    public static final int NODATE_WARINING = 1;
    public static final int NODATE_TERMINATED = 2;
    public static final int BUSINESS_WARNING = 85;
    public static final int BUSINESS_ILLEGAL_PARAMETER = 50;
    public static final int BUSINESS_CANCEL = 60;
    public static final int DATEBASE_WARNING = 75;

    public static final int FAILED = -1;
    public static final int DATEBASE_ERROR = -4;
    public static final int BUSINESS_ERROR = 80;
    public static final int SYSTEM_ERROR = 90;
    public static final int NETWORK_SYSTEM_CONNECTION_ERROR = 99;


    public String getMessageCode();

    public String getMessageDefault();

    public int getResultCode();

    public boolean isSuccess();


}
