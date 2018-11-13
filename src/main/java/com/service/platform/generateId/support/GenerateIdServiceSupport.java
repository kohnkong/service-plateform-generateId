package com.service.platform.generateId.support;

import com.service.platform.generateId.constants.Constant;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class GenerateIdServiceSupport {

    public String getKey(String head){

        if(StringUtils.isBlank(head)){
            head = "";
        }

        return Constant.DEFAULT_KEY_PREFIX + head;

    }

    public String getRegisterKeyLock(String head){

        if(StringUtils.isBlank(head)){
            head = "";
        }

        return Constant.DEFAULT_REGISTER_KEY_LOCK_PREFIX + head;

    }

    public String getKeyLock(String head){

        if(StringUtils.isBlank(head)){
            head = "";
        }

        return Constant.DEFAULT_GENERATE_KEY_LOCK_PREFIX + head;

    }


    public String formatId(){

        return null;
    }

}
