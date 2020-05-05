package com.moelyon.ktnews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.moelyon.ktnews.R;
import com.moelyon.ktnews.dto.News;

import java.util.List;

public class NewsAdapter extends ArrayAdapter<News> {
    private  int rid;
    public NewsAdapter(@NonNull Context context, int resource, @NonNull List<News> objects) {
        super(context, resource, objects);
        rid=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        News news = getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(rid,parent,false);

        TextView title = view.findViewById(R.id.new_title);
        title.setText(news.getTitle());

        return view;
    }
}
