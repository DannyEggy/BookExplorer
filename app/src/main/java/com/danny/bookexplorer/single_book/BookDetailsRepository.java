package com.danny.bookexplorer.single_book;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.danny.bookexplorer.api.ElasticAPI;
import com.danny.bookexplorer.model.Book;
import com.danny.bookexplorer.model.BookSource;
import com.danny.bookexplorer.repository.BookNetworkDataSource;
import com.danny.bookexplorer.repository.NetworkState;

import io.reactivex.disposables.CompositeDisposable;

public class BookDetailsRepository {
    private final ElasticAPI elasticAPI;
    private BookNetworkDataSource bookNetworkDataSource;

    public BookDetailsRepository(ElasticAPI elasticAPI) {
        this.elasticAPI = elasticAPI;
    }

    public BookDetailsRepository(ElasticAPI elasticAPI, BookNetworkDataSource bookNetworkDataSource) {
        this.elasticAPI = elasticAPI;
        this.bookNetworkDataSource = bookNetworkDataSource;
    }



    public MutableLiveData<BookSource> fetchSingleBookDetails(String bookID, CompositeDisposable compositeDisposable){
        bookNetworkDataSource = new BookNetworkDataSource(elasticAPI, compositeDisposable);
        bookNetworkDataSource.fetchBookDetails(bookID);
        return bookNetworkDataSource.downloadBookDetailsResponse();
    }

    public LiveData<NetworkState> getMovieDetailsNetworkState(){

        return bookNetworkDataSource.networkState();
    }
}
