package com.service.platform.generateId.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static sun.jvm.hotspot.code.CompressedStream.L;

public class DateUtil {

    /**
     * 返回当前的年月日
     * @return
     */
    public static String getNowString() {
        SimpleDateFormat formater = new SimpleDateFormat("yyyyMMdd");
        String date = "";
        try {
            date = formater.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }

    public static void main(String[] args) {
        System.out.println(getNowString());

        String prefix = "xy";
        Long num = new Long((long) 1111.0);

        String length = "6";

        System.out.println(prefix.concat(String.format("%1$0" + length +"d", num)));

    }

}
