package com.moelyon.ktnews.dto;

/**
 * @author moelyon
 */
public class Category {

    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public Category() {
    }

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
