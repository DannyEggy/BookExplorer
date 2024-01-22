package com.danny.bookexplorer.model;

import com.google.gson.annotations.SerializedName;

public class Rrf {

    @SerializedName("window_size")
    private int windowSize;

    @SerializedName("rank_constant")
    private int rankConstant;

    // Getters and setters


    public Rrf(int windowSize, int rankConstant) {
        this.windowSize = windowSize;
        this.rankConstant = rankConstant;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public int getRankConstant() {
        return rankConstant;
    }

    public void setRankConstant(int rankConstant) {
        this.rankConstant = rankConstant;
    }
}
