package com.danny.bookexplorer.model.search_request_2;

import com.danny.bookexplorer.model.search_request_1.MatchPhrase;
import com.google.gson.annotations.SerializedName;

public class Must {
    @SerializedName("match_phrase")
    private MatchPhrase2 matchPhrase;

    @SerializedName("match")
    private Match match;

    @SerializedName("range")
    private Range range;

    public Must(MatchPhrase2 matchPhrase) {
        this.matchPhrase = matchPhrase;
    }

    public Must(Match match) {
        this.match = match;
    }

    public Must(Range range) {
        this.range = range;
    }

    public MatchPhrase2 getMatchPhrase() {
        return matchPhrase;
    }

    public void setMatchPhrase(MatchPhrase2 matchPhrase) {
        this.matchPhrase = matchPhrase;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }
}
