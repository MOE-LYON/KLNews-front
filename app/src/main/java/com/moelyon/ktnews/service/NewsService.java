package com.moelyon.ktnews.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.moelyon.ktnews.dto.Category;
import com.moelyon.ktnews.dto.News;
import com.moelyon.ktnews.dto.Pagination;
import com.moelyon.ktnews.util.HttpUtil;
import com.moelyon.ktnews.util.JsonUtil;

import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * @author moelyon
 */
public class NewsService {

    public static Pagination<News> getNewsList(Map<String,String> params){
        HttpUrl.Builder httpBuilder = HttpUrl.parse(HttpUtil.getUrl("/news")).newBuilder();
        for(Map.Entry<String, String> param : params.entrySet()) {
            httpBuilder.addQueryParameter(param.getKey(),param.getValue());
        }
        Request request = new Request.Builder().url(httpBuilder.build()).get().build();
        JsonObject json = HttpUtil.makeRequest(request);

        Pagination<News> data = JsonUtil.parseJsonPagination(json.get("data"),new TypeToken<Pagination<News>>() {}.getType());
        return  data;
    }

    public static News createNews(News news){

        String jsonStr = JsonUtil.beanToJson(news);
        RequestBody body = RequestBody.create(HttpUtil.JSON,jsonStr);
        Request request = new Request.Builder().url(HttpUtil.getUrl("/news"))
                .post(body).build();

        JsonObject json = HttpUtil.makeRequest(request);

        News res = JsonUtil.parseJson(json.get("data"),News.class);

        return res;
    }

    public static boolean deleteById(int id){
        HttpUrl url= HttpUrl.parse(HttpUtil.getUrl("/news")).newBuilder()
                .addPathSegment(Integer.toString(id)).build();
        Request request = new Request.Builder().url(url).delete().build();

        JsonObject json = HttpUtil.makeRequest(request);

        return json.get("code").getAsInt()==200;
    }

    public static News getNewsDetail(int id){
        HttpUrl url= HttpUrl.parse(HttpUtil.getUrl("/news")).newBuilder()
                .addPathSegment(Integer.toString(id)).build();
        Request request = new Request.Builder().url(url).get().build();

        JsonObject json = HttpUtil.makeRequest(request);

        News news = JsonUtil.parseJson(json.get("data"),News.class);

        return  news;
    }

    public static void updateNews(News news) {

        HttpUrl url= HttpUrl.parse(HttpUtil.getUrl("/news")).newBuilder()
                .addPathSegment(Integer.toString(news.getId())).build();
        String json_str = JsonUtil.beanToJson(news);
        RequestBody body = RequestBody.create(HttpUtil.JSON,json_str);
        Request request = new Request.Builder().url(url).put(body).build();

        JsonObject json = HttpUtil.makeRequest(request);

        if(json.get("code").getAsInt()!=200){
            throw new RuntimeException("update error");
        }
    }
}
