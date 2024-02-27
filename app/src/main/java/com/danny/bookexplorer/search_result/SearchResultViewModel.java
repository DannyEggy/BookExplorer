package com.danny.bookexplorer.search_result;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.danny.bookexplorer.model.SearchResult;
import com.danny.bookexplorer.repository.NetworkState;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class SearchResultViewModel extends ViewModel {
    private ResultDetailsRepository resultDetailsRepository;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String query;
    private String knnField;
    private double[] knnQueryVector;

    private String queryTitle;
    private String queryDesc;
    private int pageCountGTE;
    private int pageCountLTE;
    private double averageRatingGTE;
    private double averageRatingLTE;

    public SearchResultViewModel(ResultDetailsRepository resultDetailsRepository, String query) {
        this.resultDetailsRepository = resultDetailsRepository;
        this.query = query;
    }

    public SearchResultViewModel(ResultDetailsRepository resultDetailsRepository, String query, String knnField, double[] knnQueryVector) {
        this.resultDetailsRepository = resultDetailsRepository;

        this.query = query;
        this.knnField = knnField;
        this.knnQueryVector = knnQueryVector;
    }

    public SearchResultViewModel(ResultDetailsRepository resultDetailsRepository, String queryTitle, String queryDesc,int pageCountGTE,
                                 int pageCountLTE, double averageRatingGTE, double averageRatingLTE) {
        this.resultDetailsRepository = resultDetailsRepository;

        this.queryTitle = queryTitle;
        this.queryDesc = queryDesc;
        this.pageCountGTE = pageCountGTE;
        this.pageCountLTE = pageCountLTE;
        this.averageRatingGTE = averageRatingGTE;
        this.averageRatingLTE = averageRatingLTE;
    }

    public MutableLiveData<SearchResult> getMultipleSearchResult(){
        return resultDetailsRepository.fetchMultipleSearchResult(queryTitle, queryDesc, pageCountGTE, pageCountLTE,
                averageRatingGTE, averageRatingLTE, compositeDisposable);
    }


    public MutableLiveData<SearchResult> getSearchResult(){
        return resultDetailsRepository.fetchSearchResult(query, compositeDisposable);
    }

    public MutableLiveData<SearchResult> getHybridSearchResult(){
        return resultDetailsRepository.fetchHybridSearchResult(query, knnField, knnQueryVector, compositeDisposable);
    }


    public LiveData<NetworkState> getNetworkState(){
        return resultDetailsRepository.getResultDetailsNetworkState();
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.dispose();
    }


}
