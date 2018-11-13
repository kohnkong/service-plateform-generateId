package com.service.platform.generateId.utils;

import com.google.gson.Gson;


public class GsonUtil {

    private static Gson gson = null;

    static{
        if(null == gson){
            gson = new Gson();
        }
    }

    public GsonUtil() {

    }


    /**
     * Object 转成 json字符串
     * @param obj
     * @return
     */
    public static String toJson(Object obj){
        String result = null;
        if(null != gson){
            result = gson.toJson(obj);
        }
        return result;
    }
}
