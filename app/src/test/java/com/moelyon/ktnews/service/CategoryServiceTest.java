package com.moelyon.ktnews.service;

import com.moelyon.ktnews.dto.Category;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CategoryServiceTest {

    @Test
    public void getAll() {

        List<Category> categories = CategoryService.getAll();
        System.out.println(categories.size());

        assertNotEquals(categories.size(),0);
    }

    @Test
    public void getById() {

        Category category = CategoryService.getById(1);
        System.out.println(category.getName());
        assertNotNull(category);
    }
}