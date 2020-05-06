package com.moelyon.ktnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.moelyon.ktnews.dto.News;
import com.moelyon.ktnews.myviews.NetImageView;
import com.moelyon.ktnews.service.NewsService;

import java.text.SimpleDateFormat;

public class NewsDetailActivity extends AppCompatActivity {

    // control view
    private ImageButton back_btn;
    private Button delete_btn,edit_btn;
    private Button next_btn,pre_btn;
    private TextView title,content,publish_date;
    private TextView category_name;
    private NetImageView imageView;

    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    // data
    private int news_id;
    private int cid;
    private String cname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        // init
        back_btn = findViewById(R.id.news_back);
        title = findViewById(R.id.news_detail_title);
        content = findViewById(R.id.news_detail_content);
        publish_date = findViewById(R.id.news_detail_pubdate);
        imageView = findViewById(R.id.news_detail_img);
        delete_btn = findViewById(R.id.news_delete);
        category_name = findViewById(R.id.news_detail_category);
        pre_btn = findViewById(R.id.news_detail_pre);
        next_btn = findViewById(R.id.news_detail_next);
        edit_btn = findViewById(R.id.news_detail_edit);

        // event handler
        back_btn.setOnClickListener((v -> {

            setResult(MainActivity.RES_CODE);
            finish();
        }));

        delete_btn.setOnClickListener((v -> {
            deleteNews();
        }));
        edit_btn.setOnClickListener((v -> {

            Intent intent = new Intent();
            intent.setClass(this,NewsEditActivity.class);
            intent.putExtra("nid",news_id);
            intent.putExtra("cid",cid);
            intent.putExtra("cname",cname);

            startActivity(intent);

        }));


        Intent intent = getIntent();
        this.news_id = intent.getIntExtra("nid",0);
        this.cid = intent.getIntExtra("cid",0);
        this.cname = intent.getStringExtra("cname");
        category_name.setText(this.cname);

        getNewsDetail();
    }


    private void getNewsDetail(){
        new Thread(()->{
            News news = null;
            try {
                news = NewsService.getNewsDetail(this.news_id);
            }catch (RuntimeException ex){
                Log.d("detail",ex.getMessage());
                finish();
            }

            News finalNews = news;
            runOnUiThread(()->{

                title.setText(finalNews.getTitle());
                content.setText("\u3000\u3000" + finalNews.getContent());
                imageView.setImageURL(finalNews.getFrontImage());
                publish_date.setText(dateFormat.format(finalNews.getPublishDate()));
            });
        }).start();
    }

    private void deleteNews(){
        if(news_id ==0){
            Toast.makeText(this, "news is't exist ", Toast.LENGTH_SHORT).show();
        }
        int newsId = news_id;
        new Thread(()->{

            boolean b = NewsService.deleteById(newsId);

            runOnUiThread(()->{
                Toast.makeText(this, "删除成功", Toast.LENGTH_SHORT).show();
                setResult(MainActivity.RES_CODE);
                finish();
            });
        }).start();
    }
}
