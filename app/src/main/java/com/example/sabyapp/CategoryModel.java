package com.example.saby;

public class CategoryModel {
    private int CategoryID;
    private String CategoryName;
    private String CategoryThumb;

    public CategoryModel(){
    }

    public CategoryModel(int CategoryID, String CategoryName, String CategoryThumb){
        this.CategoryID = CategoryID;
        this.CategoryName = CategoryName;
        this.CategoryThumb = CategoryThumb;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCategoryThumb() {
        return CategoryThumb;
    }

    public void setCategoryThumb(String categoryThumb) {
        CategoryThumb = categoryThumb;
    }
}
