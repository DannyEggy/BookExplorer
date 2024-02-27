package com.danny.bookexplorer.model.search_request_2;

import com.google.gson.annotations.SerializedName;

public class Bool {
    @SerializedName("must")
    private Must[] must;

//    public Bool(Must[] must) {
//        this.must = must;
//    }

    public Must[] getMust() {
        return must;
    }

    public void setMust(Must[] must) {
        this.must = must;
    }
}
