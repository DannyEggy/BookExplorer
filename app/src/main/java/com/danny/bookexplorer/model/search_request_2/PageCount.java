package com.danny.bookexplorer.model.search_request_2;

import com.google.gson.annotations.SerializedName;

public class PageCount {
    @SerializedName("gte")
    private int gte;

    @SerializedName("lte")
    private int lte;

    public PageCount(int gte, int lte) {
        this.gte = gte;
        this.lte = lte;
    }

    public int getGte() {
        return gte;
    }

    public void setGte(int gte) {
        this.gte = gte;
    }

    public int getLte() {
        return lte;
    }

    public void setLte(int lte) {
        this.lte = lte;
    }
}
