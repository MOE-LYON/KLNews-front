package com.moelyon.ktnews.service;

import com.moelyon.ktnews.dto.News;
import com.moelyon.ktnews.dto.Pagination;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class NewsServiceTest {

    @Test
    public void getNewsList() {
        Map<String,String> params = new HashMap<>();
        params.put("cid",Integer.toString(0));
        params.put("page",Integer.toString(1));
        Pagination<News> pagination = NewsService.getNewsList(params);
        System.out.println(pagination.getItems().size());
        assertEquals(pagination.getPage(),1);
    }

    @Test
    public void getNewsDetail() {
        News news = NewsService.getNewsDetail(1);
        System.out.println(news.getPublishDate());
        assertNotNull(news);
    }

    @Test
    public void createNews() {
        News news = new News();
    }

    @Test
    public void deleteById() {
        boolean res = NewsService.deleteById(9);
    }
}