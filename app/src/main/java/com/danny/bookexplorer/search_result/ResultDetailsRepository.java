package com.danny.bookexplorer.search_result;

import android.graphics.ColorSpace;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.danny.bookexplorer.api.ElasticAPI;
import com.danny.bookexplorer.api.huggingface.ModelAPI;
import com.danny.bookexplorer.api.huggingface.ModelClient;
import com.danny.bookexplorer.model.SearchResult;
import com.danny.bookexplorer.repository.BookNetworkDataSource;
import com.danny.bookexplorer.repository.NetworkState;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResultDetailsRepository {
    private ElasticAPI elasticAPI;

    private ModelAPI modelAPI;

    private BookNetworkDataSource bookNetworkDataSource;


    public ResultDetailsRepository(ElasticAPI elasticAPI) {
        this.elasticAPI = elasticAPI;
    }


    public ResultDetailsRepository(ModelAPI modelAPI){
        this.modelAPI = modelAPI;
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

    public MutableLiveData<SearchResult> fetchHybridSearchResult(String query, String knnField, double[] knnQueryVector, CompositeDisposable compositeDisposable){
        bookNetworkDataSource = new BookNetworkDataSource(elasticAPI, compositeDisposable);

        bookNetworkDataSource.hybridSearch(query, knnField, knnQueryVector);
        return bookNetworkDataSource.searchResult();
    }

    public MutableLiveData<SearchResult> fetchMultipleSearchResult(String queryTitle, String queryDesc, int pageCountGTE, int pageCountLTE,
            double averageRatingGTE, double averageRatingLTE , CompositeDisposable compositeDisposable){
        bookNetworkDataSource = new BookNetworkDataSource(elasticAPI, compositeDisposable);

        bookNetworkDataSource.multipleSearch(queryTitle, queryDesc, pageCountGTE, pageCountLTE, averageRatingGTE, averageRatingLTE);
        return bookNetworkDataSource.searchResult();
    }

    public LiveData<NetworkState> getResultDetailsNetworkState(){
        return bookNetworkDataSource.networkState();
    }

//    public MutableLiveData<double[]> fetchVector(String query, CompositeDisposable compositeDisposable){
//        bookNetworkDataSource = new BookNetworkDataSource(modelAPI, compositeDisposable);
//        bookNetworkDataSource.query(query);
//        return bookNetworkDataSource.downloadVectorResponse();
//    }



}
