package com.moelyon.ktnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moelyon.ktnews.R;
import com.moelyon.ktnews.dto.News;
import com.moelyon.ktnews.myviews.NetImageView;
import com.moelyon.ktnews.util.ImageUtils;

import java.text.SimpleDateFormat;
import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    private  int rid;
    private ImageUtils imageUtils = new ImageUtils(getContext());
    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
        rid=resource;
    }
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(rid,parent,false);

        TextView title = view.findViewById(R.id.new_title);
        ImageView image = view.findViewById(R.id.news_front_image);
        TextView content = view.findViewById(R.id.news_content);
        TextView pub_date = view.findViewById(R.id.news_pub_date);

        title.setText(news.getTitle());
        content.setText("\u3000\u3000"+ news.getContent());

        imageUtils.display(image,news.getFrontImage());

        pub_date.setText(dateFormat.format(news.getPublishDate()));
        return view;
    }
}
