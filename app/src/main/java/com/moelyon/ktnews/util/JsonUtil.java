package com.moelyon.ktnews.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.moelyon.ktnews.dto.Pagination;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author moelyon
 */
public class JsonUtil {

    public static String beanToJson(Object bean){
        return getGson().toJson(bean);
    }

    public static <T> T parseJson(String json,Class<T> Class){
        Gson gson = getGson();

        return gson.fromJson(json,Class);
    }

    public static <T> T parseJson(JsonElement json, Class<T> Class){
        Gson gson = getGson();

        return gson.fromJson(json,Class);
    }

    public static <T> List<T>  parseJsonArray(String json, Class<T> Class){
        Gson gson = getGson();

        return gson.fromJson(json, new TypeToken<List<T>>() {}.getType());
    }
    public static <T> List<T>  parseJsonArray(JsonElement json,Type type){
        Gson gson = getGson();

        return gson.fromJson(json,type);
    }
    public static <T> Pagination<T>  parseJsonPagination(JsonElement json,Type type ){
        Gson gson = getGson();

        return gson.fromJson(json, type);
    }


    private static Gson getGson(){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        return gson;
    }
}
