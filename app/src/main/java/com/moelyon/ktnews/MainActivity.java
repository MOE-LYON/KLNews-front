package com.moelyon.ktnews;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.moelyon.ktnews.adapter.NewsAdapter;
import com.moelyon.ktnews.dto.Category;
import com.moelyon.ktnews.dto.News;
import com.moelyon.ktnews.dto.Pagination;
import com.moelyon.ktnews.service.CategoryService;
import com.moelyon.ktnews.service.NewsService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    // views
    private GridView category_list;
    private ListView news_list;
    // data
    private int cid =0;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        category_list = findViewById(R.id.category_list);
        news_list = findViewById(R.id.news_list);

        getCategories();
        getNews();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showCategories(List<Category> categories){

        List<Map<String,Object>> categories_info = new ArrayList<>(categories.size());
//        categories_info.add(new HashMap<>().put("cname","全部"))

        categories.forEach((c)->{
            Map<String,Object> map = new HashMap<>();
            map.put("cid",c.getId());
            map.put("cname",c.getName());
            categories_info.add(map);
        });
        SimpleAdapter categoryAdapter = new SimpleAdapter(this,categories_info,R.layout.category_item,
                new String[]{"cid", "cname"}, new int[]{R.id.cid, R.id.cname});

        int width = 240*categories.size();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT);
        category_list.setLayoutParams(params);
        category_list.setAdapter(categoryAdapter);
        ;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getCategories(){
        new Thread(()->{

            List<Category> categories = CategoryService.getAll();
            Handler handler;

            runOnUiThread(()->{
                showCategories(categories);
            });
        }).start();
    }

    private void getNews(){
        new Thread(()->{

            Map<String,String> parms = new HashMap<>();
            parms.put("cid",Integer.toString(this.cid));
            Pagination<News> newsPagination =NewsService.getNewsList(parms);

            runOnUiThread(()->{
                showNews(newsPagination.getItems());
            });

        }).start();
    }

    private void showNews(List<News> news){
        NewsAdapter adpter=new NewsAdapter(this,R.layout.new_item,news );
        news_list.setAdapter(adpter);
    }
}
