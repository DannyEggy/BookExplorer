package com.danny.bookexplorer.model.search_request_1;

import com.google.gson.annotations.SerializedName;

public class MatchPhrase {

    @SerializedName("desc")
    private String desc;

    // Getters and setters

    public String getDesc() {
        return desc;
    }

    public void setDesc(String title) {
        this.desc = title;
    }
}
