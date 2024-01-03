package com.danny.bookexplorer.api;

import com.danny.bookexplorer.model.Book;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ElasticAPI {
    @GET("books/_doc/{book_id}")
    Single<Book> getBook(

        @Path("book_id") String book_id
    );
}
