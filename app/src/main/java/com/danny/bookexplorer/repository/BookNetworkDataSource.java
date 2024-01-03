package com.danny.bookexplorer.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.danny.bookexplorer.api.ElasticAPI;
import com.danny.bookexplorer.model.Book;
import com.danny.bookexplorer.model.BookSource;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BookNetworkDataSource {
    private final ElasticAPI elasticAPI;
    private final CompositeDisposable compositeDisposable;

    private final MutableLiveData<BookSource> _downloadBookDetailsResponse = new MutableLiveData<BookSource>();
    private final MutableLiveData<NetworkState> _networkState = new MutableLiveData<NetworkState>();


    public BookNetworkDataSource(ElasticAPI elasticAPI, CompositeDisposable compositeDisposable) {
        this.elasticAPI = elasticAPI;
        this.compositeDisposable = compositeDisposable;
    }

    public LiveData<NetworkState> networkState(){
        return _networkState;
    }

    public MutableLiveData<BookSource> downloadBookDetailsResponse() {
        return _downloadBookDetailsResponse;
    }

    public void fetchBookDetails(String bookID){
        _networkState.postValue(NetworkState.LOADING);

        try {
            compositeDisposable.add(
                elasticAPI.getBook(bookID)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                bookResponse -> {
                                    BookSource book = bookResponse.getSource();
                                    _downloadBookDetailsResponse.postValue(book);
                                    _networkState.postValue(NetworkState.LOADED);
                                },
                                throwable -> {
                                    Log.e("BookDetailsDataSource", throwable.getMessage());
                                    _networkState.postValue(NetworkState.ERROR);
                                }
                        )
            );
        }catch (Exception e){
            _networkState.postValue(new NetworkState(NetworkState.Status.FAILED, "Something went wrong"));
            Log.e("BookDetailsDataSource", e.getMessage());
        }
    }
}
