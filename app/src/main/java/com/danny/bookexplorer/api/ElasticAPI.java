package com.danny.bookexplorer.api;

import com.danny.bookexplorer.model.Book;
import com.danny.bookexplorer.model.search_request_1.SearchRequest;
import com.danny.bookexplorer.model.SearchResult;
import com.danny.bookexplorer.model.search_request_2.SearchRequest2;
//import com.danny.bookexplorer.model.search_request_2.Query;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ElasticAPI {
    @GET("books/_doc/{book_id}")
    Single<Book> getBook(

        @Path("book_id") String book_id
    );

    @GET("books/_search")
    Single<SearchResult> searchBooks(
            @Query("q") String query);

    @POST("books/_search")
    Single<SearchResult> hybridSearch(
            @Body SearchRequest searchRequest
            );

    @POST("books/_search")
    Single<SearchResult> multipleSearch(
            @Body SearchRequest2 searchRequest2
    );
}

