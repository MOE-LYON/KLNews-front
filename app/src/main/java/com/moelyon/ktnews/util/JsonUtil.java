package com.moelyon.ktnews.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * @author moelyon
 */
public class JsonUtil {

    public static String beanToJson(Object bean){
        return new Gson().toJson(bean);
    }

    public static <T> T parseJson(String json,Class<T> Class){
        Gson gson = new Gson();

        return gson.fromJson(json,Class);
    }

    public static <T> List<T>  parseJsonArray(String json, Class<T> Class){
        Gson gson = new Gson();

        return gson.fromJson(json, new TypeToken<List<T>>() {}.getType());
    }
}
