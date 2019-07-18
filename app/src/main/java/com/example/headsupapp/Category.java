package com.example.headsupapp;

public class Category {

    private String name;
    private int numOfItems;
    private int coverImage;

    Category(String name, int numOfItems, int coverImage) {
        this.name = name;
        this.numOfItems = numOfItems;
        this.coverImage = coverImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    public void setNumOfItems(int numOfItems) {
        this.numOfItems = numOfItems;
    }

    public int getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(int coverImage) {
        this.coverImage = coverImage;
    }
}
