package com.danny.bookexplorer.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Hits {
    @SerializedName("hits")
    private List<Hit> hits;

    // Các phương thức getter và setter ở đây

    public List<Hit> getHits() {
        return hits;
    }

    public void setHits(List<Hit> hits) {
        this.hits = hits;
    }
}
