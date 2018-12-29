package com.test.util;

import java.util.HashMap;
import java.util.Map;

public class ToolUtil {

    public static Map<String,String> mapStringToMap(String str){
        str=str.substring(1, str.length()-1);
        String[] strs=str.split(",");
        Map<String,String> map = new HashMap<String, String>();
        for (String string:strs){
            String key = string.split("=")[0];
            String value = string.split("=")[0];
            map.put(key,value);
        }
        return map;
    }
}
