package com.danny.bookexplorer.api;

import com.danny.bookexplorer.model.Book;
import com.danny.bookexplorer.model.SearchResult;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ElasticAPI {
    @GET("books/_doc/{book_id}")
    Single<Book> getBook(

        @Path("book_id") String book_id
    );

    @GET("books/_search")
    Single<SearchResult> searchBooks(@Query("q") String query);
}
