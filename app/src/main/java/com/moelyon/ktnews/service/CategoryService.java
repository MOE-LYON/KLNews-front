package com.moelyon.ktnews.service;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.moelyon.ktnews.dto.Category;
import com.moelyon.ktnews.util.HttpUtil;
import com.moelyon.ktnews.util.JsonUtil;

import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Request;

/**
 * @author moelyon
 */
public class CategoryService {

    public static List<Category> getAll(){

        Request request = new Request.Builder().url(HttpUtil.getUrl("/category")).get().build();
        JsonObject json = HttpUtil.makeRequest(request);

        List<Category> categories = JsonUtil.parseJsonArray(json.get("data"),new TypeToken<List<Category>>() {}.getType());
        return categories;
    }

    public static Category getById(int id){

        HttpUrl url= HttpUrl.parse(HttpUtil.getUrl("/category")).newBuilder()
                .addPathSegment(Integer.toString(id)).build();
        Request request = new Request.Builder().url(url).get().build();

        JsonObject json = HttpUtil.makeRequest(request);

        Category category = JsonUtil.parseJson(json.get("data"), Category.class);
        return category;
    }

}
