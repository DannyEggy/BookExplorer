package com.danny.bookexplorer.model;

import com.google.gson.annotations.SerializedName;

public class Hit {
    @SerializedName("_id")
    private String id;

    @SerializedName("_index")
    private String index;

    @SerializedName("_score")
    private double score;

    @SerializedName("_source")
    private BookSource source;

    // Các phương thức getter và setter ở đây

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public BookSource getSource() {
        return source;
    }

    public void setSource(BookSource source) {
        this.source = source;
    }
}
