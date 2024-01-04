package com.danny.bookexplorer.model;

import com.google.gson.annotations.SerializedName;

public class SearchResult {
    @SerializedName("hits")
    private Hits hits;

    // Các phương thức getter và setter ở đây

    public Hits getHits() {
        return hits;
    }

    public void setHits(Hits hits) {
        this.hits = hits;
    }
}
