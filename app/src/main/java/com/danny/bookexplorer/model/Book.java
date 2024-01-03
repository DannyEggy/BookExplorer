package com.danny.bookexplorer.model;

import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("_id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("_source")
    private BookSource source;

    // Constructor, getter, setter (hoặc có thể sử dụng lombok để tự động sinh getter, setter)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookSource getSource() {
        return source;
    }

    public void setSource(BookSource source) {
        this.source = source;
    }
}

