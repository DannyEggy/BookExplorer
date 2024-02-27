package com.danny.bookexplorer.model.search_request_2;

import com.google.gson.annotations.SerializedName;

public class Match {
    @SerializedName("desc")
    private String desc;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
