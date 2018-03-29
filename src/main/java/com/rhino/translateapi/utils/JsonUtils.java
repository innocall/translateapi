package com.rhino.translateapi.utils;

import net.sf.json.JSONArray;

import java.util.HashMap;

public class JsonUtils {

    public static String mapToJson(HashMap<String,Object> map) {
        JSONArray json = JSONArray.fromObject(map);
        return json.toString();
    }
}
