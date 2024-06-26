package com.danny.bookexplorer.model.search_request_1;

import com.google.gson.annotations.SerializedName;

public class Query {

    @SerializedName("match_phrase")
    private MatchPhrase matchPhrase;

    // Getters and setters

    public MatchPhrase getMatchPhrase() {
        return matchPhrase;
    }

    public void setMatchPhrase(MatchPhrase matchPhrase) {
        this.matchPhrase = matchPhrase;
    }
}
