package com.danny.bookexplorer.search_result;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.danny.bookexplorer.api.ElasticAPI;
import com.danny.bookexplorer.model.SearchResult;
import com.danny.bookexplorer.repository.BookNetworkDataSource;
import com.danny.bookexplorer.repository.NetworkState;

import io.reactivex.disposables.CompositeDisposable;

public class ResultDetailsRepository {
    private final ElasticAPI elasticAPI;
    private BookNetworkDataSource bookNetworkDataSource;

    public ResultDetailsRepository(ElasticAPI elasticAPI) {
        this.elasticAPI = elasticAPI;
    }

    public ResultDetailsRepository(ElasticAPI elasticAPI, BookNetworkDataSource bookNetworkDataSource) {
        this.elasticAPI = elasticAPI;
        this.bookNetworkDataSource = bookNetworkDataSource;
    }

    public MutableLiveData<SearchResult> fetchSearchResult(String query, CompositeDisposable compositeDisposable){
        bookNetworkDataSource = new BookNetworkDataSource(elasticAPI, compositeDisposable);
        bookNetworkDataSource.searchBooks(query);
        return bookNetworkDataSource.searchResult();
    }

    public MutableLiveData<SearchResult> fetchHybridSearchResult(String query, String knnField, String knnQueryVector, CompositeDisposable compositeDisposable){
        bookNetworkDataSource = new BookNetworkDataSource(elasticAPI, compositeDisposable);
        bookNetworkDataSource.hybridSearch(query, knnField, knnQueryVector);
        return bookNetworkDataSource.searchResult();
    }

    public LiveData<NetworkState> getResultDetailsNetworkState(){
        return bookNetworkDataSource.networkState();
    }
}
