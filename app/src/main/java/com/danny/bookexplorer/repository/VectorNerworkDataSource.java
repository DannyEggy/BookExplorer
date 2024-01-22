package com.danny.bookexplorer.repository;

import androidx.lifecycle.MutableLiveData;

import com.danny.bookexplorer.api.ElasticAPI;
import com.danny.bookexplorer.api.huggingface.ModelAPI;
import com.danny.bookexplorer.model.BookSource;
import com.danny.bookexplorer.model.SearchResult;

import io.reactivex.disposables.CompositeDisposable;

public class VectorNerworkDataSource {
    private ModelAPI modelAPI;
    private CompositeDisposable compositeDisposable;

    private MutableLiveData<double[]> _downloadVectorResponse = new MutableLiveData<double[]>();

    public MutableLiveData<double[]> downloadVectorResponse() {
        return _downloadVectorResponse;
    }

}
