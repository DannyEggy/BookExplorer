package com.danny.bookexplorer.model;

import com.google.gson.annotations.SerializedName;

public class Knn {

    @SerializedName("field")
    private String field;

    @SerializedName("query_vector")
    private double[] queryVector;

    @SerializedName("k")
    private int k;

    @SerializedName("num_candidates")
    private int numCandidates;

    // Getters and setters


    public Knn(String field, double[] queryVector, int k, int numCandidates) {
        this.field = field;
        this.queryVector = queryVector;
        this.k = k;
        this.numCandidates = numCandidates;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public double[] getQueryVector() {
        return queryVector;
    }

    public void setQueryVector(double[] queryVector) {
        this.queryVector = queryVector;
    }

    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public int getNumCandidates() {
        return numCandidates;
    }

    public void setNumCandidates(int numCandidates) {
        this.numCandidates = numCandidates;
    }
}
