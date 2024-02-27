package com.danny.bookexplorer.model.search_request_1;

import com.google.gson.annotations.SerializedName;

public class MatchPhrase {

    @SerializedName("title")
    private String title;

    // Getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
