package com.moelyon.ktnews;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.moelyon.ktnews.dto.Category;
import com.moelyon.ktnews.dto.News;
import com.moelyon.ktnews.service.CategoryService;
import com.moelyon.ktnews.service.NewsService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;

public class NewsEditActivity extends AppCompatActivity {

    private News news;
    private int nid;
    private int cid;
    private List<Category> categoryList;


    private Button save_btn,back_btn;
    private Spinner category_spiner;
    private EditText title,content;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_edit);

        save_btn = findViewById(R.id.news_edit_save);
        back_btn = findViewById(R.id.news_edit_back);
        category_spiner = findViewById(R.id.news_edit_category_spiner);
        content = findViewById(R.id.news_edit_content);
        title = findViewById(R.id.news_edit_title);

        Intent intent = getIntent();

        nid = intent.getIntExtra("nid",0);
        cid = intent.getIntExtra("cid",0);

        back_btn.setOnClickListener((v -> {
            finish();
        }));
        save_btn.setOnClickListener((v)->{
            saveNews();
        });

        getNewsDetail();
        getCategories();
    }

    private void getNewsDetail() {
        if(nid>0){
            new Thread(() -> {
                News news = null;
                try {
                    news = NewsService.getNewsDetail(this.nid);
                } catch (RuntimeException ex) {
                    Log.d("detail", ex.getMessage());
                    finish();
                }
                this.news = news;
                News finalNews = news;
                runOnUiThread(() -> {
                    title.setText(finalNews.getTitle());
                    content.setText(finalNews.getContent());
                });
            }).start();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void getCategories(){
        new Thread(()->{

            List<Category> categories = CategoryService.getAll();
            categoryList = categories;
            runOnUiThread(()->{
                //showCategories(categories);
                String[] array = categories.stream().map(Category::getName).toArray(String[]::new);
                SpinnerAdapter adapter =  new ArrayAdapter<String>(this,
                        android.R.layout.simple_dropdown_item_1line,array );
                category_spiner.setAdapter(adapter);

                Category category = null;

                for (Category cate : categories){
                    if(cid == cate.getId()){
                        category = cate;
                        break;
                    }
                }

                int current_idx = categories.indexOf(category);
                if(current_idx>=0){
                    category_spiner.setSelection(current_idx);
                }else{
                    category_spiner.setSelection(0);
                }

            });
        }).start();
    }

    private void saveNews(){
        if(this.news != null){
            updateNews();
        }else{
            addNews();
        }
    }


    private void addNews(){

        String item = (String) category_spiner.getSelectedItem();
        int cid = getCnameByCid(item);

        News news = new News(title.getText().toString(),
                cid,"",content.getContext().toString());

        new Thread(()->{
            NewsService.createNews(news);

            runOnUiThread(this::finish);
        }).start();
    }

    private void  updateNews(){
        String item = (String) category_spiner.getSelectedItem();
        int cid = getCnameByCid(item);
        news.setCid(cid);
        news.setTitle(title.getText().toString());
        news.setContent(content.getText().toString());

        new Thread(()->{
            NewsService.updateNews(news);

            runOnUiThread(this::finish);
        }).start();
    }

    private int getCnameByCid(String cname){

        int cid = 0;

        for (Category c: categoryList){
            if(cname.equals(c.getName())){
                cid = c.getId();
                break;
            }
        }

        return  cid;

    }
}
