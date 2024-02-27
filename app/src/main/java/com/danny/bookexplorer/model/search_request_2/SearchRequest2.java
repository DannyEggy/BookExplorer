package com.danny.bookexplorer.model.search_request_2;

import com.google.gson.annotations.SerializedName;

public class SearchRequest2 {
    @SerializedName("query")
    private Query2 query;

    public Query2 getQuery() {
        return query;
    }

    public void setQuery(Query2 query) {
        this.query = query;
    }
}
