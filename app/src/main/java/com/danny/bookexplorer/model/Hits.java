package com.danny.bookexplorer.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hits {
    @SerializedName("hits")
    private List<BookSource> hitList;

    public List<BookSource> getHitList() {
        return hitList;
    }

    public void setHitList(List<BookSource> hitList) {
        this.hitList = hitList;
    }
}
