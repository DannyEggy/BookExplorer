package com.danny.bookexplorer.model.search_request_1;

import com.google.gson.annotations.SerializedName;

public class Rank {

    @SerializedName("rrf")
    private Rrf rrf;

    // Getters and setters

    public Rrf getRrf() {
        return rrf;
    }

    public void setRrf(Rrf rrf) {
        this.rrf = rrf;
    }
}
