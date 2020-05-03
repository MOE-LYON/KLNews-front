package com.moelyon.ktnews.util;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author moelyon
 */
public class HttpUtil {

    public void makeRequestAsync(Request request, Callback callback){
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(callback);
    }

    public String makeRequest(Request request){
        OkHttpClient client = new OkHttpClient();
        try {
            Response response = client.newCall(request).execute();

            assert response.body() != null;
            return response.body().string();
        } catch (IOException e) {
            return null;
        }
    }

}
