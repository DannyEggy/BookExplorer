package com.danny.bookexplorer.search_result;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.danny.bookexplorer.model.SearchResult;
import com.danny.bookexplorer.repository.NetworkState;

import io.reactivex.disposables.CompositeDisposable;

public class SearchResultViewModel extends ViewModel {
    private ResultDetailsRepository resultDetailsRepository;
    private CompositeDisposable compositeDisposable;
    private String query;

    public SearchResultViewModel(ResultDetailsRepository resultDetailsRepository, String query) {
        this.resultDetailsRepository = resultDetailsRepository;
        this.query = query;
    }

    public MutableLiveData<SearchResult> getSearchResult(){
        return resultDetailsRepository.fetchSearchResult(query, compositeDisposable);
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
