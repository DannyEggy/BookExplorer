package com.danny.bookexplorer.model;

import com.google.gson.annotations.SerializedName;

public class BookSource {
    @SerializedName("title")
    private String title;

    @SerializedName("desc")
    private String description;

    @SerializedName("authors")
    private String authors;

    @SerializedName("categories")
    private String categories;

    @SerializedName("averagerating")
    private double averageRating;

    @SerializedName("maturityrating")
    private String maturityRating;

    @SerializedName("publisheddate")
    private String publishedDate;

    @SerializedName("pagecount")
    private int pageCount;

    // Constructor, getter, setter (hoặc có thể sử dụng lombok để tự động sinh getter, setter)

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public String getMaturityRating() {
        return maturityRating;
    }

    public void setMaturityRating(String maturityRating) {
        this.maturityRating = maturityRating;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }
}
