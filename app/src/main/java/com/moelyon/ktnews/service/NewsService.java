package com.moelyon.ktnews.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.moelyon.ktnews.dto.News;
import com.moelyon.ktnews.dto.Pagination;
import com.moelyon.ktnews.util.HttpUtil;
import com.moelyon.ktnews.util.JsonUtil;

import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.Request;

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
        Pagination<News> data = JsonUtil.parseJsonPagination(json.get("data"),News.class);
        return  data;
    }

    public static News createNews(News news){


        return news;
    }

    public static News getNewsDetail(int id){
        HttpUrl url= HttpUrl.parse(HttpUtil.getUrl("/news")).newBuilder()
                .addPathSegment(Integer.toString(id)).build();
        Request request = new Request.Builder().url(url).get().build();

        JsonObject json = HttpUtil.makeRequest(request);

        News news = JsonUtil.parseJson(json.get("data"),News.class);

        return  news;
    }
}
