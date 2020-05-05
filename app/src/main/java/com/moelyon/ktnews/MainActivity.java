package com.moelyon.ktnews;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
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

    private static final String TAG = "MainActivity";
    // views
    private GridView category_list;
    private ListView news_list;
    private ImageButton add_btn;
    // data
    private int cid =0;
    private String cname = "全部";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        category_list = findViewById(R.id.category_list);
        news_list = findViewById(R.id.news_list);
        add_btn = findViewById(R.id.news_add);
        // get data from net
        getCategories();
        getNews();

        // handler event
        category_list.setOnItemClickListener((parent, view, position, id) -> {

            HashMap<String,Object>  map = (HashMap) parent.getItemAtPosition(position);
            int cate_id = (Integer) map.get("cid");
            if (cate_id != this.cid){
                getNews();
                this.cid = cate_id;
                this.cname = (String) map.get("cname");
            }

        });

        news_list.setOnItemClickListener((parent, view, position, id) -> {

            News news = (News) parent.getItemAtPosition(position);
            Intent intent = new Intent();
            intent.setClass(this,NewsDetailActivity.class);

            intent.putExtra("nid",news.getId());
            intent.putExtra("cid",news.getCid());
            intent.putExtra("cname",cname);
            startActivity(intent);
        });
        add_btn.setOnClickListener((v)->{

            Intent intent = new Intent();
            intent.setClass(this,NewsEditActivity.class);

            startActivity(intent);


        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getCategories(){
        new Thread(()->{

            List<Category> categories = CategoryService.getAll();
            categories.add(0,new Category(0,"全部"));

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

    private void showNews(List<News> news){
        NewsAdapter adpter=new NewsAdapter(this,R.layout.new_item,news );
        news_list.setAdapter(adpter);
    }
}
