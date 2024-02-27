package com.danny.bookexplorer.model.search_request_2;

import com.google.gson.annotations.SerializedName;

public class AverageRating {
    @SerializedName("gte")
    private double gte;

    @SerializedName("lte")
    private double lte;

    public AverageRating(double gte, double lte) {
        this.gte = gte;
        this.lte = lte;
    }

    public double getGte() {
        return gte;
    }

    public void setGte(double gte) {
        this.gte = gte;
    }

    public double getLte() {
        return lte;
    }

    public void setLte(double lte) {
        this.lte = lte;
    }
}
