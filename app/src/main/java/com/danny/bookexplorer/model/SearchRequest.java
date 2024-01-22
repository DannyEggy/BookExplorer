package com.danny.bookexplorer.model;

import com.google.gson.annotations.SerializedName;

public class SearchRequest {

    @SerializedName("_source")
    private String[] source;

    @SerializedName("query")
    private Query query;

    @SerializedName("knn")
    private Knn knn;

    @SerializedName("rank")
    private Rank rank;

    // Getters and setters

    public SearchRequest() {

    }

    public String[] getSource() {
        return source;
    }

    public void setSource(String[] source) {
        this.source = source;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public Knn getKnn() {
        return knn;
    }

    public void setKnn(Knn knn) {
        this.knn = knn;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
}

