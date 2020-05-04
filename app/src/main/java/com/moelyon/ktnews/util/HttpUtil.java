package com.moelyon.ktnews.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author moelyon
 */
public class HttpUtil {

    public static void makeRequestAsync(Request request, Callback callback){
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }

    public static JsonObject makeRequest(Request request){
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();

            assert response.body() != null;
            String json = response.body().string();

            JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

            if (jsonObject.get("code").getAsInt()!=200){
                throw new RuntimeException("request error");
            }
            return jsonObject;
        } catch (IOException e) {
            return null;
        }
    }

    public static final String BaseUrl = "https://klnews.moelyon.com/api";

    public static String getUrl(String url){
        return String.format("%s%s",BaseUrl,url);
    }

}
