package com.moelyon.ktnews;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.moelyon.ktnews.dto.News;
import com.moelyon.ktnews.dto.Pagination;
import com.moelyon.ktnews.util.JsonUtil;

import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void getNews(){
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url("https://klnews.moelyon.com/api/news/1").get().build();

        try {
            Response response = client.newCall(request).execute();

            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            JsonObject jsonObject = new JsonParser().parse(response.body().string()).getAsJsonObject();

            if (jsonObject.get("code").getAsInt()!=200){
                throw new RuntimeException("request error");
            }

            News news = gson.fromJson(jsonObject.get("data"), News.class);
            System.out.println(news);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getNewsList(){
        OkHttpClient client = new OkHttpClient();
        Map<String,String> params = new HashMap<>();
        params.put("cid",Integer.toString(0));
        HttpUrl.Builder httpBuilder = HttpUrl.parse("https://klnews.moelyon.com/api/news").newBuilder();
        for(Map.Entry<String, String> param : params.entrySet()) {
            httpBuilder.addQueryParameter(param.getKey(),param.getValue());
        }
        Request request = new Request.Builder().url(httpBuilder.build()).get().build();

        try {
            Response response = client.newCall(request).execute();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            JsonObject jsonObject = new JsonParser().parse(response.body().string()).getAsJsonObject();
            if (jsonObject.get("code").getAsInt()!=200){
                throw new RuntimeException("request error");
            }
            Pagination<News> data = gson.fromJson(jsonObject.get("data"), new TypeToken<Pagination<News>>() {
            }.getType());

            System.out.println(data.getItems().size());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}