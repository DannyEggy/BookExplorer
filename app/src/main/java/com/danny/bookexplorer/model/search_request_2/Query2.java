package com.danny.bookexplorer.model.search_request_2;

import com.google.gson.annotations.SerializedName;

public class Query2 {
    @SerializedName("bool")
    private Bool bool;

    public Bool getBool() {
        return bool;
    }

    public void setBool(Bool bool) {
        this.bool = bool;
    }
}
