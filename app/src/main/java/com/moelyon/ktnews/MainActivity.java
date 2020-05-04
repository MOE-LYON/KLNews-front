package com.moelyon.ktnews;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.moelyon.ktnews.dto.Category;
import com.moelyon.ktnews.service.CategoryService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private GridView category_list;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        category_list = findViewById(R.id.category_list);

        getCategories();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void showCategories(List<Category> categories){

        List<Map<String,Object>> categories_info = new ArrayList<>(categories.size());
        categories.forEach((c)->{
            Map<String,Object> map = new HashMap<>();
            map.put("cid",c.getId());
            map.put("cname",c.getName());
            categories_info.add(map);
        });
        SimpleAdapter categoryAdapter = new SimpleAdapter(this,categories_info,R.layout.category_item,
                new String[]{"cid", "cname"}, new int[]{R.id.cid, R.id.cname});

        int width = 200*categories.size();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, LinearLayout.LayoutParams.MATCH_PARENT);

        category_list.setLayoutParams(params);
        category_list.setAdapter(categoryAdapter);
        ;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getCategories(){
        new Thread(()->{

            List<Category> categories = CategoryService.getAll();

            runOnUiThread(()->{
                showCategories(categories);
            });
        }).start();
    }
}
